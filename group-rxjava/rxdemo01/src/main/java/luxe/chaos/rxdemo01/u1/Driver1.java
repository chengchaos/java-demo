package luxe.chaos.rxdemo01.u1;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/3/2021 2:38 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Driver1 {

    public void start() {

        MyFlowImpl.MyPublisher<Integer> publisher = new MyFlowImpl.MyPublisher<>();
        MyFlowImpl.MySubscriber<Integer> subscriber = new MyFlowImpl.MySubscriber<>();
        publisher.subscribe(subscriber);



    }
}
