package cn.futuremove.mocktbox.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @author chengchaos[as]Administrator - 2019/4/17 0017 上午 10:25 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@RestController
public class SayHaiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SayHaiController.class);


    @GetMapping(value = "/v1/say/{hai}")
    public WebAsyncTask<String> hai(@PathVariable(name = "hai") String hai) {

        return new WebAsyncTask<>(() -> "Hai !");

    }
}
