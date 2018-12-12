package com.example.mystores.controller;

import com.example.mystores.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/v1/users/{userId}")
    public Map<String, Object> getUser(@PathVariable(name = "userId") Integer userId) {

        Map<String, Object> result = userService.getUser(userId);

        return result;
    }

    @GetMapping(value = "/v1/users/name/{name}")
    public Map<String, Object> getUserByName(@PathVariable(name="name") String name) {

        return userService.getUserByName(name);
    }
}
