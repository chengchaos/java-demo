package com.example.myribbons.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myribbons.ribbons.UserService;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;
    
    @GetMapping(value = "/v1/users/{userId}")
    public Map<String, Object> getUser(@PathVariable(name = "userId") Integer userId) {

        Map<String, Object> result = userService.getUser(userId);

        return result;
    }

    @GetMapping(value = "/v1/users/name/{name}")
    public Map<String, Object> getUserByName(@PathVariable(name="name") String name) {

        return userService.getUserByName(name);
    }
    
    @GetMapping("/v1/system/service-instances/{name}")
    public List<ServiceInstance> showServiceInstances(
            @PathVariable(name="name") String name) {
        return this.discoveryClient
                .getInstances(name);
    }
}
