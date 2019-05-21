package cn.futuremove.mocktbox.config;

import cn.futuremove.mocktbox.entity.User;
import cn.futuremove.mocktbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/17 0017 下午 6:19 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Component
public class Init implements CommandLineRunner {

    @Autowired
    private UserService userService;


    @Override
    public void run(String... args) throws Exception {

        userService.setUser("1",new User("1", "carlos",18));
        userService.setUser("2",new User("2", "lisa",19));
        userService.setUser("3",new User("3", "mike",17));
        userService.setUser("4",new User("4", "tom",16));
        userService.setUser("5",new User("5", "amy",15));
    }
}
