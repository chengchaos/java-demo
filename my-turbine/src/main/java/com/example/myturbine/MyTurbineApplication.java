package com.example.myturbine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableTurbine
public class MyTurbineApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyTurbineApplication.class);


	public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(MyTurbineApplication.class, args);

        LOGGER.info("Eureka is started ... {}", context);
    }
}
