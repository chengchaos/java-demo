package cn.springcloud.book.ch6.controller;

import cn.springcloud.book.ch6.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {



    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/ch6/user-info")
    public String userInfo(String username) {

        String result = userService.getUser(username);
        LOGGER.info("result -=>  {}", result);

        return result;

    }

}
