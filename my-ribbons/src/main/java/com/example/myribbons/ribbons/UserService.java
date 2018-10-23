package com.example.myribbons.ribbons;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    @Qualifier(value="loadBalancedRestTemplate")
    private RestTemplate restTemplate;
    
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public String choose() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("MY-STORES");

        String info = String.format("%s:%s:%s", serviceInstance.getServiceId(),
                serviceInstance.getHost(),
                serviceInstance.getPort());

        LOGGER.info("{}", info);
        System.err.println("restTemplate: "+ restTemplate);
        return info;
    }

    @HystrixCommand(fallbackMethod = "getUserFallback")
    public Map<String, Object> getUser(Integer userId) {

        String uriString = "http://MY-STORES/v1/users/{userId}";

        Map<String, Object> params = Collections.singletonMap("userId", userId);
        URI uri = UriComponentsBuilder
                .fromUriString(uriString)
                .build()
                .expand(params)
                .encode()
                .toUri();


        String result = restTemplate.getForObject(uri, String.class);
        LOGGER.info("uri: {} => result: {}", uri, result);

        Map<String, Object> json = JSON.parseObject(result);

        return json;

    }
    
    public Map<String, Object> getUserFallback(Integer userId, Throwable throwable) {
        
        LOGGER.error("Fallback");
        LOGGER.error("Fallback", throwable);
        
        return Collections.singletonMap("user", "default");
    }

    public Map<String, Object> getUserByName(String name) {

        String uriString = "http://MY-STORES/v1/users/name/{name}";

        URI uri = new DefaultUriBuilderFactory(uriString)
                .builder()
                .build(Collections.singletonMap("name", name));


        String result = restTemplate.getForObject(uri, String.class);
        LOGGER.info("uri: {}; result: {}", uri, result);

        Map<String, Object> json = JSON.parseObject(result);

        return json;

    }
}
