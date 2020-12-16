package luxe.chaos.netty.keepalive.server;

import luxe.chaos.netty.keepalive.server.handler.Server;
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
public class ServerApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ServerApp.class, args);

        Server server = ctx.getBean(Server.class);
        server.start(9999);


    }
}
