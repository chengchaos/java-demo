package cn.springcloud.book.ch5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/add")
    public String add(Integer a, Integer b) {

        String url = "http://ribbon-server/add?a=" + a + "&b=" + b;
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }
}
