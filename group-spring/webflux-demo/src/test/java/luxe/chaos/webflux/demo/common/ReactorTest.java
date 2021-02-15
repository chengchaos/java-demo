package luxe.chaos.webflux.demo.common;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/3/2021 5:38 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class ReactorTest {

    private class IntSubscriber implements Subscriber<Integer> {

        private Subscription s;
        @Override
        public void onSubscribe(Subscription s) {
            (this.s = s).request(1L);
        }

        @Override
        public void onNext(Integer item) {
            System.out.println(" i => "+ item);
            this.s.request(1L);
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {
        }

    }
    @Test
    public void streamTest() {
        String[] str = {"1", "2", "3"};

        Flux.fromArray(str)
                .map(Integer::parseInt)
                .subscribe(new IntSubscriber());
    }
}
