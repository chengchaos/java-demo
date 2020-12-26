package luxe.chaos.netty.keepalive.client;

import luxe.chaos.netty.keepalive.client.config.ProjectConfig;
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
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 12/16/2020 5:01 PM <br />
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientAppTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAppTest.class);

    @Autowired
    private ProjectConfig projectConfig;

    @Test
    public void contextLoads() {

    }

    @Test
    public void ProjectConfig() {

        Assert.assertNotNull("what?", projectConfig);

        boolean kafka = this.projectConfig.getAutoStartConfig().isKafka();
        boolean netty = this.projectConfig.getAutoStartConfig().isNetty();
        int port = this.projectConfig.getNettyConfig().getPort();

        LOGGER.info("kafka {}, netty {}, port {}", kafka, netty, port);
    }
}
