package cn.springcloud.book.ch2.feign.service;

import org.springframework.stereotype.Component;

@Component
public class UserInfoServiceFallback implements UserInfoService {
    @Override
    public String getUser(String username) {
        return "Fallback method say: user is NOT exists!!!";
    }
}
