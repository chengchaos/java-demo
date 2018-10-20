package com.example.mystores.ribbons;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;


@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> getUser(Integer userId) {

        String uriString = "http://MY-STORES/v1/users/{userId}";

        URI uri = UriComponentsBuilder
                .fromUriString(uriString)
                .build()
                .expand(Collections.singletonMap("userId", userId))
                .encode()
                .toUri();


        String result = restTemplate.getForObject(uri, String.class);
        LOGGER.info("uri: {}; result: {}", uri, result);

        Map<String, Object> json = JSON.parseObject(result);

        return json;

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
