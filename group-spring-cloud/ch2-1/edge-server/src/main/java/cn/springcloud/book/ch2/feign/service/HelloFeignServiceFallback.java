package cn.springcloud.book.ch2.feign.service;

public class HelloFeignServiceFallback implements HelloFeignService {
    @Override
    public String searchRepo(String queryString) {
        return "{\"message\":\"nothing\"}";
    }
}
