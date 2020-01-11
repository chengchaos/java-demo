package com.example.myscala002.controller;

import com.example.myscala002.netty.EchoClient;
import com.example.myscala002.netty.EchoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/10 0010 下午 7:30 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@RestController
public class CtrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CtrlController.class);

    private final static String ERROR_MESSAGE = "Task error";
    private final static String TIME_MESSAGE = "Task timeout";

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor executor;

    @GetMapping(value="/v1/start")
    public String start() {

        this.executor.execute(() -> new EchoServer(7777).start());

        return "OK";

    }

    @GetMapping(value="/v1/conn")
    @ResponseBody
    public WebAsyncTask<String> conn() {

        WebAsyncTask<String> asyncTask= new WebAsyncTask<>(10 * 1000L, executor, () -> {
            new EchoClient("localhost", 7777).start();
            return "OK";
        });

        // 任务执行完成时调用该方法
        asyncTask.onCompletion(() -> LOGGER.info("任务执行完成"));
        LOGGER.info("继续处理其他事情");

        return asyncTask;
    }
}
