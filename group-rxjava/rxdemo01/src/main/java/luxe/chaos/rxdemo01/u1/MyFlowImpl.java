package luxe.chaos.rxdemo01.u1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/3/2021 2:39 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class MyFlowImpl {

    private static final Logger logger = LoggerFactory.getLogger(MyFlowImpl.class);

    static class MyPublisher<Integer> implements Flow.Publisher {

        @Override
        public void subscribe(Flow.Subscriber subscriber) {

        }
    }

    static class MySubscriber<Integer> implements Flow.Subscriber {

        @Override
        public void onSubscribe(Flow.Subscription subscription) {

        }

        @Override
        public void onNext(Object item) {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }

    static class MySubscription<Integer> implements Flow.Subscription {

        @Override
        public void request(long n) {

        }

        @Override
        public void cancel() {

        }
    }
}
