package cn.springcloud.book.ch13.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class MyActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyActuator.class);

    public MyActuator() {
        super();
        LOGGER.info("+++++++++++++++++++++ My Acturator");
    }

    @GetMapping(value = "/actuator/health")
    public String health() {

        return "SUCCESS";

    }

    @GetMapping(value = "/ch13/say-hai")
    public Map<String, Object> sayHai() {
        return Collections.singletonMap("message", "嗨 .. Consul");
    }
}
