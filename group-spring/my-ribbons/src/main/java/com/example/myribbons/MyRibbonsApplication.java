package com.example.myribbons;


import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
//@EnableAutoConfiguration
//@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
public class MyRibbonsApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyRibbonsApplication.class);


    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet() {

        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean =
                new ServletRegistrationBean<>(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");

        return registrationBean;
    }


	public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(MyRibbonsApplication.class, args);

        LOGGER.info("MyRibbonsApplication is started.");
    }
}
