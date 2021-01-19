package cn.springcloud.book.service;

import cn.springcloud.book.beans.User;

public interface UserService {

    User getUser(String username, String password);

    long addUser(User user);
}
