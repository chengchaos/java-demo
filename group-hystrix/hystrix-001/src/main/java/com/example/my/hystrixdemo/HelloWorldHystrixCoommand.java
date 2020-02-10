package com.example.my.hystrixdemo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldHystrixCoommand extends HystrixCommand<String> {


    private final String name;

    public HelloWorldHystrixCoommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    /**
     * 如果继承的是 HystrixObservableCommand
     * 要重写 Observable construct()
     *
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
        return "Hello Hystrix, your name is "+ name;
    }
}
