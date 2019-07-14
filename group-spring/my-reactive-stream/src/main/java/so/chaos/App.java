package so.chaos;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Flow flow = null;

        // 1. 定义发布者, 发布的数据类型是 Integer:
        // 使用 jdk 自带的 SubmissionPublisher 它实现了 Publisher 接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;

            /**
             * 建立订阅关系时调用
             *
             * 保存订阅关系, 需要用它来给发布者相应
             * @param subscription
             */
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                // 请求一个数据
                // n == 需要多少数据
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                // 接收到一个数据
                System.out.println("收到数据: "+ item);

                // 再请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {

                throwable.printStackTrace();
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {

                System.out.println("完");
            }
        };

        publisher.subscribe(subscriber);

        // 4. 生产数据
        for (int i = 0; i < 10; i++) {

            publisher.submit(i);
        }


        // 5. 关闭发布者

        publisher.close();

        try {
            Thread.currentThread().join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
