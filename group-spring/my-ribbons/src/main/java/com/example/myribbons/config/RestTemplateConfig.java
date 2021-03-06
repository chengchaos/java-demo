package com.example.myribbons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateConfig.class);


    @Bean(name="loadBalancedRestTemplate")
    @LoadBalanced
    public RestTemplate restTemplate() {

        LOGGER.info("初始化 RestTemplate ...");
        RestTemplate restTemplate = new RestTemplate();
        System.err.println("restTemplate: "+ restTemplate);
        return restTemplate;
    }
}
