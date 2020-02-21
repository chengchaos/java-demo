package luxe.chaos.demo;

import grpc.HelloWorldClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoClient01App {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoClient01App.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoClient01App.class, args);
        LOGGER.info("it worded!");

        HelloWorldClient.execute();
    }
}
