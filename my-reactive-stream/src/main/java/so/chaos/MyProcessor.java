package so.chaos;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2019-04-26 19:48 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class MyProcessor extends SubmissionPublisher<Integer> implements Flow.Subscriber<Integer> {
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Integer item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
