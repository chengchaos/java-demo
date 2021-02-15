package luxe.chaos.webflux.demo.service.impl;

import luxe.chaos.webflux.demo.WebfluxDemoApplication;
import luxe.chaos.webflux.demo.enties.User;
import luxe.chaos.webflux.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/4/2021 11:54 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@SpringBootTest(classes = WebfluxDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    private final UserService userService;

    public UserServiceTest(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testAdd() {
        User user = new User();
        user.setName("程超");
        user.setAge(35);

        String id = this.userService.add(user).block().getId();
        logger.info("id = {}", id);
    }

    @Test
    public void testGetById() {

        String id = "601b969ca07d3b5ae74ffc1d";
        this.userService.findById(id)
                .flatMap(u -> {
                    logger.info("u = {}", u);
                    return Mono.empty();
                })
                .block();

    }
    @Test
    public void testUpdate() {
        User user = new User();
        user.setName("程超");
        user.setAge(35);

        String id = this.userService.update(user).block().getId();
        logger.info("id = {}", id);

    }

    @Test
    public void testUpdate2() {

        String id = "601b969ca07d3b5ae74ffc";
        User user = new User();
        user.setName("程超");
        user.setAge(353);

        Object x = this.userService.findById(id)
//                .map(u -> u.setAge(user.getAge()).setName(user.getName()))
                .flatMap(u -> {
                    logger.info("input user => {}", user);
                    u.setName(user.getName()).setAge(666);
                    return this.userService.update(u);
                })
                .map(u2 -> new ResponseEntity<User>(u2, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
//                .block();
        ;
        logger.info("x = {}", x);

    }
}
