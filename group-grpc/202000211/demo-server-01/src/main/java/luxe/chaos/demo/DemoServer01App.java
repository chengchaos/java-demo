package luxe.chaos.demo;

import luxe.chaos.demo.config.EnvironmentDemo;
import luxe.chaos.demo.config.MyWebsite;
import luxe.chaos.demo.config.ReportProperties;
import luxe.chaos.demo.grpc.HelloWorldServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoServer01App {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoServer01App.class);


    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoServer01App.class, args);
        LOGGER.info("it worded!");

        ReportProperties report = ctx.getBean(ReportProperties.class);

        report.getHosts()
                .forEach(host -> LOGGER.info(">>> {}:{}", host.getHost(), host.getPort()));

        MyWebsite myWebsite = ctx.getBean(MyWebsite.class);
        LOGGER.info(">>> my-website url -=> {}", myWebsite.getUrl());

        EnvironmentDemo environmentDemo = ctx.getBean(EnvironmentDemo.class);

        String serverName = environmentDemo.getServerName();

        LOGGER.info(">>> server-name -=> {}", serverName);

        new Thread(HelloWorldServer::serverStart);

    }
}
