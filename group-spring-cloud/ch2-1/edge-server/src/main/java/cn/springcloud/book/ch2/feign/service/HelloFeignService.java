package cn.springcloud.book.ch2.feign.service;

import cn.springcloud.book.ch2.feign.config.HelloFeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "github-client",
        url = "https://www.chengchaos.cn",
        configuration = HelloFeignServiceConfig.class,
        fallback = HelloFeignServiceFallback.class
)
public interface HelloFeignService {

    /**
     *
     */
    @RequestMapping(value = "/search/repositories", method = RequestMethod.GET)
    String searchRepo(@RequestParam("q") String queryString);
}
