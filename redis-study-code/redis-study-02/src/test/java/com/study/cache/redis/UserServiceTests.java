package com.study.cache.redis;

import com.study.cache.redis.config.AppConfig;
import com.study.cache.redis.pojo.User;
import com.study.cache.redis.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext.xml"})
@WebAppConfiguration("src/main/resources")
public class UserServiceTests {
    @Autowired
    UserService userService;

    @Test
    public void test0() {
        User user = userService.findUserById("10001");
        System.out.println(user.getUname());
    }

    @Test
    public void test1() {
        System.out.println(userService.findUserNameById("10001"));
    }
    @Test
    public void test2() {
        User user = new User();
        user.setUid("10001");
        user.setUname("tony2");
        userService.updateUser(user);
    }
}
