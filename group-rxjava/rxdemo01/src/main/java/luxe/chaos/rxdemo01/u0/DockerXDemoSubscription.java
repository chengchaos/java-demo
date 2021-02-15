package luxe.chaos.rxdemo01.u0;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/2/2021 3:04 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DockerXDemoSubscription<T>  implements Flow.Subscription {

    private final Flow.Subscriber<? super T> subscriber;
    private final ExecutorService executor;
    private Future<?> future;
    private T item;
    private boolean completed;


    public void setFuture(Future<T> future) {
        this.future = future;
    }

    public Flow.Subscriber<? super T> getSubscriber() {
        return subscriber;
    }

    public DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
        this.subscriber = subscriber;
        this.executor = executor;
    }

    @Override
    public void request(long n) {
        if (n != 0 && !completed) {
            if (n < 0) {
                IllegalArgumentException ex = new IllegalArgumentException();
                executor.execute(() -> subscriber.onError(ex));
            } else {
                future = executor.submit(() -> subscriber.onNext(item));
            }
        } else {
            subscriber.onComplete();
        }

    }

    @Override
    public void cancel() {
        this.completed = true;
        if (future != null && !future.isCancelled()) {
            this.future.cancel(true);
        }
    }
}
