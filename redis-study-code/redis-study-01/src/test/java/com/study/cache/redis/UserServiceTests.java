package com.study.cache.redis;

import com.study.cache.redis.pojo.User;
import com.study.cache.redis.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceTests {
    @Autowired
    UserService userService;

    @Test
    public void test0(){
        User user = userService.findUserById("10001");
        System.out.println(user.getUname());
    }
}
