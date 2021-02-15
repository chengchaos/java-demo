package luxe.chaos.rxdemo01.u0;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/3/2021 2:37 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Driver0 {


    public void start() {
        ExecutorService executorService = ForkJoinPool.commonPool();
        try (DockerXDemoPublisher<Integer> publisher = new DockerXDemoPublisher<>(executorService)) {

            publisher.subscribe(new DockerXDemoSubscriber<>(4L, "One"));
            publisher.subscribe(new DockerXDemoSubscriber<>(4L, "Two"));
            publisher.subscribe(new DockerXDemoSubscriber<>(4L, "Three"));
            publisher.subscribe(new DockerXDemoSubscriber<>(4L, "Four"));
            publisher.subscribe(new DockerXDemoSubscriber<>(4L, "Five"));
            IntStream
                    .range(1, 50)
                    .forEach(publisher::submit);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                executorService.shutdown();
                int shutdownDelaySec = 1;
                System.out.println("等待 " + shutdownDelaySec + " 秒后服务结束.");
                executorService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("调用 executorService.shutdownNow() 来结束服务");
                List<Runnable> l = executorService.shutdownNow();
                System.out.println("还剩下 " + l.size() + " 个任务等待执行。");
            }
        }
    }
}
