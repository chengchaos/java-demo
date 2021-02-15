package luxe.chaos.webflux.demo.service.impl;

import luxe.chaos.webflux.demo.WebfluxDemoApplication;
import luxe.chaos.webflux.demo.service.UserServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
public class UserServiceApiTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceApiTest.class);

    private final UserServiceApi userServiceApi;

    public UserServiceApiTest(@Autowired UserServiceApi userServiceApi) {
        this.userServiceApi = userServiceApi;
    }
}
