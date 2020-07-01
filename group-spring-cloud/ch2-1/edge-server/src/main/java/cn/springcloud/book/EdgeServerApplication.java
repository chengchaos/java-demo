package cn.springcloud.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.springcloud.book.ch2.feign.service")
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class EdgeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeServerApplication.class, args);
    }

}