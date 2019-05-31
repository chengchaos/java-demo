package com.example.demo.controller;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2019-04-26 20:20 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@RestController
public class TestController {

    @GetMapping("/1")
    public String get1() {

        return "some string 1";
    }

    @GetMapping("/2")
    public Mono<String> get2() {
        return Mono.just("mono just 2");
    }
    @GetMapping("/3")
    public Mono<String> get3() {
        return Mono.fromSupplier(() -> "hello world");
    }

    @GetMapping(value="/4", produces = "text/event-stream")
    public Flux<String> get4() {
        return Flux
                .fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(5L);
                    } catch (Exception e ) {
                    }
                    return "flux + "+ i;
                }));
    }

    public static void main(String[] args) {
        // reactor = jdk8 stream + jdk9 reactive
        // Nono 0 --> 1 个元素
        // Flux 0 --> n 个元素
        String[] strs = {"1", "2", "3"};
        Flux.fromArray(strs)
                .map(Integer::parseInt)
                // 订阅
                .subscribe(new Subscriber<>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {

                        this.subscription = subscription;
                        subscription.request(1);

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(" >> "+ integer);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        this.subscription.cancel();
                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }
}
