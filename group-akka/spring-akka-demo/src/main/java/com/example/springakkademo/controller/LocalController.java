package com.example.springakkademo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * https://blog.csdn.net/xiejiefeng333/article/details/84107907
 *
 * @see com.example.springakkademo.config.AsyncInterceptor
 * @see com.example.springakkademo.config.WebMvcConfig
 * @see com.example.springakkademo.web.MyFilter
 */
@RestController
public class LocalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalController.class);

    @GetMapping("/ac")
    public WebAsyncTask<String> asyncTest() {


        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("--- asyncTest ---");
            return "success";
        });


        return new WebAsyncTask<>(2000L, future::get);
    }
}
