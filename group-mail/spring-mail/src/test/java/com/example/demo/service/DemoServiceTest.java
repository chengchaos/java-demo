package com.example.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoServiceTest {

    static Logger LOGGER = LoggerFactory.getLogger(DemoServiceTest.class);

    @Autowired
    private DemoService demoService;


    @Test
    void getNameTest() {
        Assertions.assertNotNull(demoService, "DemoService 不应该为 null！");
        String name = this.demoService.getUserName();
        LOGGER.info("name => {}", name);
    }
}
