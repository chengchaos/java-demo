package cn.springcloud.book.ch2.feign.service;

import cn.springcloud.book.ch2.feign.config.HelloFeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "ribbon-server",
        configuration = HelloFeignServiceConfig.class,
        fallback = UserInfoServiceFallback.class
)
public interface UserInfoService {

    @RequestMapping(value = "/ch6/user-info", method = RequestMethod.GET)
    String getUser(@RequestParam(name="username") String username);
}
