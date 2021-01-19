package cn.springcloud.book.ch5.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/add")
    public String add(Integer a, Integer b, HttpServletRequest request) {

        try {
            logger.info("休眠 {} 毫秒", a);
            TimeUnit.MILLISECONDS.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("执行 add ");
        return "From Port: "+ request.getServerPort()  +
                ", Result: " +
                (a + b);
    }
}
