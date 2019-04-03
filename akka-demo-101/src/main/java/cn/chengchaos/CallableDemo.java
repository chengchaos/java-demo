package cn.chengchaos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/17 0017 下午 6:10 <br />
 * @since 1.1.0
 */
public class CallableDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallableDemo.class);

    public static void main(String[] args) {
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(2L);
            return "hello world";
        };

        Runnable runnable;


        Future<String> future;
        RunnableFuture runnableFuture;

        ExecutorService service = Executors.newFixedThreadPool(1);

        Future<String> submit = service.submit(callable);

        // submit class is ... ==> java.util.concurrent.FutureTask
        LOGGER.info("submit class is ... ==> {}", submit.getClass().getName());

        try {
            LOGGER.info("is done 1 ? ==> {}", submit.isDone());
            String result = submit.get(3000L, TimeUnit.MILLISECONDS);
            LOGGER.info("is done 2 ? ==> {}", submit.isDone());
            LOGGER.info("result ==> {}", result);
        } catch (TimeoutException | ExecutionException e) {
            LOGGER.error("", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("", e);
        }
    }
}
