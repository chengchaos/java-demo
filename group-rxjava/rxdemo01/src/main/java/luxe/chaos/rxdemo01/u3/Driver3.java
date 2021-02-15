package luxe.chaos.rxdemo01.u3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/3/2021 2:46 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Driver3 {

    private static final Logger logger = LoggerFactory.getLogger(Driver3.class);

    public void start() {
        // 直接使用 jdk 自带的 SubmissionPublisher
        // 其实现了 Publisher 接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // 自定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                logger.info("onSubscribe ...");
                this.subscription = subscription;
                this.subscription.request(1);
                logger.info("this subscription => {}", this.subscription);
            }

            @Override
            public void onNext(Integer item) {
                logger.info("Item => {}", item);

                try {
                    int r = ThreadLocalRandom.current().nextInt(1000);
                    logger.info("waiting ... {}", r);
                    TimeUnit.MILLISECONDS.sleep(r);
                    this.subscription.request(1);
                } catch (InterruptedException ie) {
                    logger.warn("ie", ie);
                    Thread.currentThread().interrupt();
                }

                if (item >= 100) {
                    this.subscription.cancel();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error("onError ... ", throwable);
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                logger.info("onComplete ... 在 Publisher close 时触发");
            }
        };

//        publisher.subscribe(subscriber);

        MyProcessor myProcessor = new MyProcessor();
        int maxBufferCapacity = myProcessor.getMaxBufferCapacity();

        logger.info("maxBufferCapacity => {}", maxBufferCapacity);

        myProcessor.subscribe(subscriber);
        publisher.subscribe(myProcessor);




        try {
            for (int i = 0; i < 1024; i++) {
                publisher.submit(i);
            }
        } finally {
            publisher.close();
        }

        logger.info("++++");
        try {
            Thread.currentThread().join(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("----");
    }
}
