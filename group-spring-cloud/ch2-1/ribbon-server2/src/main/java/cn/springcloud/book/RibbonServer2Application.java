package cn.springcloud.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "cn.springcloud.book.ch2.feign.service")
public class RibbonServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(RibbonServer2Application.class, args);
    }
}
