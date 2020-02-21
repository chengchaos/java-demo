package luxe.chaos.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Map;

public class EchoController {


    private static final Logger LOGGER = LoggerFactory.getLogger(EchoController.class);


    @GetMapping(value = "/v1/say-hai")
    public Map<String, Object> sayHello() {

        return Collections.singletonMap("message", "嗨..");
    }
}
