package cn.futuremove.mocktbox.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.Map;


/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/17 0017 上午 10:41 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
//@Controller
public class EchoController {


    private static final Logger LOGGER = LoggerFactory.getLogger(EchoController.class);

    private static final long TIMEOUT = 5000L;

    @GetMapping(value="/echo")
    public WebAsyncTask<String> echo(Map<String, Object> model) {

        return new WebAsyncTask<>(TIMEOUT, ()->{

            LOGGER.info(" ... ");

            model.put("name", "程超");
            return "echo";
        });

    }

}
