package cn.springcloud.book.controller;

import cn.springcloud.book.beans.User;
import cn.springcloud.book.entities.DataWrapper;
import cn.springcloud.book.feign.Server2Client;
import cn.springcloud.book.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 9:56 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Server2Client server2Client;

    @GetMapping(value = "/ch6/add")
    public String testAdd(@RequestParam int a, @RequestParam int b) {

        logger.info("a => {}, b => {}", a, b);
        if (a == 1) {
            return (a + b) + "";
        } else {
            return this.server2Client.add(a, b);
        }
    }

    public static class UserSignInData {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/v1/user-sign-in")
    public DataWrapper<String> userSignIn(@RequestBody Map<String, String> input) {

        Assert.notNull(input, "UserSignInData must be not null!");
        String username = input.get("username");
        String password = input.get("password");

        if (StringUtils.isAnyEmpty(username, password)) {
            return DataWrapper.wrapError("User name / Password must be has value.");
        }

        User user = this.userService.getUser(username, password);

        if (user != null) {
            return DataWrapper.wrapData(UUID.randomUUID().toString());
        }

        return DataWrapper.wrapError("Account unable.");

    }
}
