package luxe.chaos.rxdemo01.rx2;

import java.util.concurrent.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/2/2021 5:14 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class FutureDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("running ...");
                TimeUnit.SECONDS.sleep(2L);
                return "complete";
            }
        });

        System.out.println("Do ...");

        try {
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
