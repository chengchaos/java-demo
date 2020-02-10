package cn.springcloud.book.ch2.feign.controller;

import cn.springcloud.book.ch2.feign.service.HelloFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloFeignController {

    @Autowired
    private HelloFeignService helloFeignService;

    @GetMapping(value="/search/github")
    public String searchGithubRepoByStr(@RequestParam("str") String str ) {

        return helloFeignService.searchRepo(str);
    }
}
