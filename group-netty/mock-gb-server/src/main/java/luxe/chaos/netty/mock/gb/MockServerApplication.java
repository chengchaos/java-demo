package luxe.chaos.netty.mock.gb;

import luxe.chaos.netty.mock.gb.server.MockServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 12/16/2020 3:12 PM <br />
 */
@SpringBootApplication
public class MockServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MockServerApplication.class, args);

        MockServer mockServer = ctx.getBean("mockServer", MockServer.class);
        mockServer.start(60000);
    }
}
