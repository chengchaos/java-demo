package luxe.chaos.rxdemo01.u3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/3/2021 3:11 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class MyProcessor extends SubmissionPublisher<Integer> implements Flow.Processor<Integer, Integer>{

    private static final Logger logger = LoggerFactory.getLogger(MyProcessor.class);

    private Flow.Subscription subscription;


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1L);
    }

    @Override
    public void onNext(Integer item) {
        subscription.request(1);
//        submit(function.apply(item));
        logger.info("Received item => {}", item);
        if (item % 2 == 0) {
            // 这里是阻塞的。
            submit(item);
        }
//        this.subscription.request(1L);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        this.subscription.request(1L);
    }

    @Override
    public void onComplete() {
        this.close();
    }

    @Override
    public int submit(Integer item) {
        return super.submit(item);
    }
}
