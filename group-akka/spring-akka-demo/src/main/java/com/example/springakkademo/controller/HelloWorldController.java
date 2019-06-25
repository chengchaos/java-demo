package com.example.springakkademo.controller;

import com.example.springakkademo.actor.ActorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import scala.Option;

@RestController
public class HelloWorldController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);


    @Autowired
    private ActorManager actorManager;

    @GetMapping("/hello/calc1/{val}")
    public Integer calc(@PathVariable Integer val) {

        //LOGGER.info("input ==> {}", val);

        return val + 1;
    }

    @GetMapping("/hello/calc2/")
    public Integer calc2(Integer val) {


        Option<Integer> result = this.actorManager.calc(val);
        LOGGER.info("计算结果：==> {}", result);

        if (result.isEmpty()) {
            return -1;
        }


        return result.get();
    }



    @GetMapping("/hello/calc3/")
    public Integer calc3(Integer val) {


        Option<Integer> result = this.actorManager.calc2(val);
        LOGGER.info("3 の 计算结果：==> {}", result);

        if (result.isEmpty()) {
            return -1;
        }


        return result.get();
    }
}
