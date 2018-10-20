package com.example.mystores.service;

import com.example.mystores.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Map<String, Object> getUser(Integer userId) {

        return this.userDao.getUser(userId);
    }

    public Map<String, Object> getUserByName(String name) {

        return this.userDao.getUserByName(name);
    }
}
