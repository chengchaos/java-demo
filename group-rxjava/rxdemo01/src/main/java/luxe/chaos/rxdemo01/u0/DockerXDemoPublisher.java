package luxe.chaos.rxdemo01.u0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/2/2021 2:17 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>, AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(DockerXDemoPublisher.class);

    private final ExecutorService executor; // daemon-based

    private CopyOnWriteArrayList<DockerXDemoSubscription> list = new CopyOnWriteArrayList<>();


    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(T item) {
        logger.info("### *** 开始发布元素 Item : {}", item);
        list.forEach(e -> e.setFuture(executor.submit(() -> e.getSubscriber().onNext(item))));
    }


    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {

        DockerXDemoSubscription subscription = new DockerXDemoSubscription(subscriber, executor);
//        subscriber.onSubscribe(new DockerXDemoSubscription(subscriber, executor));
//        list.add(new DockerXDemoSubscription(subscriber, executor));
        subscriber.onSubscribe(subscription);
        this.list.add(subscription);
    }


    @Override
    public void close() throws Exception {
        logger.info("close ........");
        list.forEach(e ->
                e.setFuture(executor.submit(e.getSubscriber()::onComplete)));
    }
}
