package luxe.chaos.netty.keepalive.client;

import luxe.chaos.netty.keepalive.client.handler.EchoClient;
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
 * @since [产品模块版本]
 */
@SpringBootApplication
public class ClientApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ClientApp.class, args);
        EchoClient echoClient = ctx.getBean(EchoClient.class);
        echoClient.connect(9999);
    }
}
