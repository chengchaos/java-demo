package cn.springcloud.book.ch6.controller;

import cn.springcloud.book.ch2.feign.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/ch6/user-info1")
    public String userInfo(String username) {

        String url = "http://ribbon-server/ch6/user-info?username="+ username;
        LOGGER.info("url -=> {}", url);

        String result = restTemplate.getForObject(url, String.class);
        LOGGER.info("result -=>  {}", result);

        return result;

    }

    @GetMapping("/ch6/user-info2")
    public String userInfo2(String username) {
        return this.userInfoService.getUser(username);
    }


    @GetMapping(value = "/ch6/say-hai")
    public Map<String, Object> sayHai() {
        return Collections.singletonMap("message", "嗨..");
    }
}
