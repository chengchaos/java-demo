package cn.springcloud.book.ch2.feign.service;

import org.springframework.stereotype.Component;

@Component
public class HelloFeignServiceFallback implements HelloFeignService {
    @Override
    public String searchRepo(String queryString) {
        return "{\"message\":\"nothing\"}";
    }
}
