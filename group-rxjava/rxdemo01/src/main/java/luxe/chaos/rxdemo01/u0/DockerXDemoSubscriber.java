package luxe.chaos.rxdemo01.u0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/2/2021 2:06 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T>{

    private static final Logger logger = LoggerFactory.getLogger(DockerXDemoSubscriber.class);

    private String name;
    private Flow.Subscription subscription;
    final long bufferSize;
    long count;

    public DockerXDemoSubscriber(long bufferSize,
                                 String name) {
        this.bufferSize = bufferSize;
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

        (this.subscription = subscription).request(bufferSize);
        logger.info("开始 onSubscribe 订阅");
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            logger.error("error", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onNext(T item) {

        logger.info("Subscriber 调用 onNext => name: {}, item: {} *** === *** ####",
                this.name, item);
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        } catch(InterruptedException e) {
            logger.error("interrupted", e);
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        logger.info("### *** === *** Completed!");
    }
}
