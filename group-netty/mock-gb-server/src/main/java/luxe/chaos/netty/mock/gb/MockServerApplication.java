package luxe.chaos.netty.mock.gb;

import luxe.chaos.netty.mock.gb.config.ProjectConfig;
import luxe.chaos.netty.mock.gb.server.MockServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MockServerApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MockServerApplication.class, args);

        ProjectConfig projectConfig = ctx.getBean(ProjectConfig.class);
        MockServer mockServer = ctx.getBean("mockServer", MockServer.class);

        if (projectConfig.isNettyAutoStart()) {
            int port = projectConfig.getNettyPort();
            logger.info("启动 Netty ，监听端口： {}", port);
            mockServer.start(port);
        }
    }
}
