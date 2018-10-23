package com.example.myhystrixdashboard;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixDashboardApplication.class);


	public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(HystrixDashboardApplication.class, args);

        LOGGER.info("Eureka is started ... {}", context);
    }
}
