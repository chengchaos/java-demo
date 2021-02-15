package luxe.chaos.rxdemo01.rx2;

import io.reactivex.Observable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/2/2021 4:44 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Rx2Demo {

    public static void main(String[] args) {

        Observable<Object> observable = Observable.create(observer -> {
            observer.onNext("处理的数字是：  "+ ThreadLocalRandom.current().nextInt(100));
            observer.onNext("处理的数字是：  "+ ThreadLocalRandom.current().nextInt(100));
            observer.onComplete();
       }).cache();

        System.out.println("下面两行代码会打印两个不同的数字");
        System.out.println("observable 只有在得到观察者订阅的时候才会下发元素。");


        observable.subscribe(consumer -> System.out.println("1: 我处理的是:"+ consumer));
        observable.subscribe(consumer -> System.out.println("2: 我处理的是:"+ consumer));
    }
}
