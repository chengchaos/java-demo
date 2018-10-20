package com.example.mystores;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class StoresApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoresApplication.class);


	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StoresApplication.class, args);

        LOGGER.info("Eureka is started.");
    }
}
