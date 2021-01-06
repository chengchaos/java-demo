package cn.springcloud.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/4/2021 5:53 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class MyActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyActuator.class);

    public MyActuator() {
        super();
    }

    @GetMapping(value = "/actuator/health")
    public String health() {

        return "SUCCESS";

    }
}
