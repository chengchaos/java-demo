package luxe.chaos.threads.demo001;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/20/2021 2:09 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DemoInterrupt {

    private static final Logger logger = LoggerFactory.getLogger(DemoInterrupt.class);

    public static void main(String[] args) {

        logger.info("start.");

        Thread t2 = new Thread(new SomeRunnable());
        t2.setDaemon(false);
        t2.start();

        try {
            logger.info("主线程休眠 1。");
            TimeUnit.SECONDS.sleep(3L);
            // t2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        // 当前线程被中断会怎样？
        // 如果当前线程中断，t2 不是 Daemon 线程，不受影响。
        Thread.currentThread().interrupt();

        try {
            logger.info("主线程休眠 2。");
            TimeUnit.SECONDS.sleep(3L);
            t2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        logger.info("stop.");
    }



    static class SomeRunnable implements Runnable {

        @Override
        public void run() {

            while(!Thread.interrupted()) {
                logger.info("time .. {}", new Date());
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
