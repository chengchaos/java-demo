package luxe.chaos.springboot.demo.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/7/2021 11:29 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class OneLevelAsyncContext {

    private static final Logger logger = LoggerFactory.getLogger(OneLevelAsyncContext.class);

    private static final String PARA_NAME_URI = "uri";
    private static final String PARA_NAME_PARAMS = "params";


    private final int corePoolSize;
    private final int maximumPoolSize;

    private final int queueCapacity;
    private int keepAliveTimeInSeconds = 300;
    private long syncTimeoutInSeconds = 300;

    private LinkedBlockingDeque<Runnable> queue;
    private ThreadPoolExecutor executor;


    private AsyncListener asyncListener;

    private volatile boolean start = false;

    public OneLevelAsyncContext() {
        this.corePoolSize = 128;
        this.maximumPoolSize = 1024;
        this.queueCapacity = 32;
        this.afterPropertiesSet();
        this.start = true;
//        this.executor.submit(doInspect());

        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(() -> inspect(), 1, 3, TimeUnit.SECONDS);
    }

    public void shutdown() {
        if (this.start) {
            List<Runnable> tasks = this.executor.shutdownNow();
            logger.info("关闭后未执行任务数量： {}", tasks.size());
            this.start = false;
        }
    }

    /**
     * @param req
     * @param task
     */
    public void submitFuture(final HttpServletRequest req,
                             final Callable<Object> task) {

        final String uri = req.getRequestURI();
        final Map<String, String[]> params = req.getParameterMap();
        final AsyncContext asyncContext = req.startAsync();

        asyncContext.getRequest().setAttribute(PARA_NAME_URI, uri);
        asyncContext.getRequest().setAttribute(PARA_NAME_PARAMS, params);
        asyncContext.setTimeout(syncTimeoutInSeconds * 1000);

        if (asyncListener != null) {
            asyncContext.addListener(asyncListener);
        }


        executor.submit(new CanceledCallable(asyncContext) {

            @Override
            public Object call() throws Exception {
                // 业务处理调用
                Object o = task.call();
                if (o == null) {
                    // 业务完成后响应处理
                    callback(asyncContext, "");
                }
                if (o instanceof CompletableFuture) {
                    CompletableFuture<Object> future = (CompletableFuture<Object>) o;
                    future.thenAccept(
                            resultObject -> callback(asyncContext, resultObject))
                            .exceptionally(e -> {
                                callback(asyncContext, "");
                                return null;
                            });
                } else if (o instanceof String) {
                    callback(asyncContext, o);
                }
                return null;
            }
        });

    }

    private void callback(AsyncContext asyncContext, Object result) {

        HttpServletResponse resp =
                (HttpServletResponse) asyncContext.getResponse();
        try {
            if (result instanceof String) {
                write(resp, (String) result);
            } else {
                write(resp, JacksonHelper.toJson(result).orElse("NULL"));
            }
        } catch (Exception e) {
            this.logError(asyncContext, "Callback");
            this.writeInternalServerError(asyncContext);
            logger.error("", e);
        } finally {
            asyncContext.complete();
        }
    }

    private void write(HttpServletResponse resp, String content) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = resp.getWriter();
        writer.write(content);
        writer.flush();
    }

    public void afterPropertiesSet() {

        queue = new LinkedBlockingDeque<>(queueCapacity);

        executor = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTimeInSeconds, TimeUnit.SECONDS,
                queue);
        executor.allowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if (r instanceof CanceledCallable) {
                    CanceledCallable cc = (CanceledCallable) r;
                    AsyncContext asyncContext = cc.asyncContext;
                    logError(asyncContext, "Reject");
                    writeInternalServerError(asyncContext);
                }
            }
        });

        if (asyncListener == null) {
            asyncListener = new AsyncListener() {

                @Override
                public void onComplete(AsyncEvent asyncEvent) throws IOException {

                }

                @Override
                public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                    AsyncContext asyncContext = asyncEvent.getAsyncContext();
                    logError(asyncContext, "Timeout");
                    writeInternalServerError(asyncContext);
                }

                @Override
                public void onError(AsyncEvent asyncEvent) throws IOException {
                    AsyncContext asyncContext = asyncEvent.getAsyncContext();
                    logError(asyncContext, "Error");
                    writeInternalServerError(asyncContext);
                }

                @Override
                public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

                }
            };
        }
    }

    void logError(AsyncContext asyncContext, String info) {
        if (asyncContext != null) {
            try {
                ServletRequest request = asyncContext.getRequest();
                Object uri = request.getAttribute(PARA_NAME_URI);
                Object params = request.getAttribute(PARA_NAME_PARAMS);
                logger.error("async request {}, uri: {}, params: {}",
                        info, uri, params);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    void writeInternalServerError(AsyncContext asyncContext) {

        try {
            ((HttpServletResponse) asyncContext.getRequest())
                    .setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            asyncContext.complete();
        }
    }

    void inspect() {
        logger.info("{} CompletedTaskCount: {}, TaskCount: {}, ActiveCount: {}, Pending tasks: {}, LargestPoolSize: {}",
                "OneLevel",
                executor.getCompletedTaskCount(),
                executor.getTaskCount(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                executor.getLargestPoolSize());

    }

    Runnable doInspect() {
        return new Thread() {
            @Override
            public void run() {
                while(!isInterrupted()) {
                    inspect();
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        };
    }
}
