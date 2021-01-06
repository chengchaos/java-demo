package luxe.chaos.tcd.tt;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 *
 *
 * 差不多可以看出，一个线程占 1 M 左右的内存。如果线程很大。
 * 记得提前设置好 Xmx 的参数值。
 * </p>
 *
 * @author Cheng, Chao - 12/31/2020 7:52 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Enter {

    public static void main(String[] args) {

        int count =  600;
        if (args.length > 0) {
            count = Integer.parseInt(args[0]);
        }

        System.out.printf("count => %d%n%n", count);

        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {

                        System.err.println(Thread.currentThread().getName() + " => hello");

                        TimeUnit.MINUTES.sleep(1L);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            try {
                TimeUnit.MILLISECONDS.sleep(5L);
                System.out.println("Thread : -> " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
