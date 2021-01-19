package cn.springcloud.book.service.impl;

import cn.springcloud.book.UserServerApplication;
import cn.springcloud.book.beans.User;
import cn.springcloud.book.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 2:03 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void addUserTest() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("Charles Cheng");
        user.setPassword("Nihao!World");

        long userId = this.userService.addUser(user);
        logger.info("save user get id => ", userId);
    }

    @Test
    public void getUserTest() {

        String username = "Charles Cheng";
        String password = "Nihao!World";

        User user = this.userService.getUser(username, password);

        logger.info("user => {}", user.getUserName());
        logger.info("password => {}", user.getPassword());

        Assert.assertNotNull("Must bu not null!", user);
        Assert.assertEquals(username, user.getUserName());
    }
}
