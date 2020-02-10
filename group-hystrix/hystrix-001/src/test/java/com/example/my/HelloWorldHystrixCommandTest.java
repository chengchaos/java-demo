package com.example.my;

import com.example.my.hystrixdemo.HelloWorldHystrixCoommand;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloWorldHystrixCommandTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldHystrixCommandTest.class);

    @Test
    public void test001() {

        /* 调用程序对 HelloWorldHystrixCommand 实例化，
         * 执行execute() 即触发 HelloWorldHystrixCommand.run()的执行
         */
        HelloWorldHystrixCoommand cmd = null;
        cmd = new HelloWorldHystrixCoommand("chengchao");
        String result1 = cmd.execute();
        LOGGER.info("result1 is ... {}", result1);

        cmd = new HelloWorldHystrixCoommand("chengchao");
        Future<String> stringFuture = cmd.queue();

        String result2 = null;
        try {
            result2 = stringFuture.get(500, TimeUnit.MILLISECONDS);
            LOGGER.info("result2 is ... {}", result2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        LOGGER.info("--end--");
    }
}
