package cn.springcloud.book.ch6.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @HystrixCommand(fallbackMethod="defaultUser")
    public String getuser(String username) throws Exception {
        if (username.equals("spring")) {
            return "this is REAL user";
        } else {
            throw new Exception("this is NOT REAL user");
        }
    }

    public String defaultUser(String username) throws Exception {
        return "The user does not exists in this system.";
    }
}
