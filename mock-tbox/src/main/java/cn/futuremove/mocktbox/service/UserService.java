package cn.futuremove.mocktbox.service;

import cn.futuremove.mocktbox.entity.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static reactor.core.publisher.Flux.fromArray;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/17 0017 下午 5:57 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Service
public class UserService {

    private Map<String, User> userMap = new ConcurrentHashMap<>();

    private static final User DEFAULT_USER = new User("0", "NULL", 0);



    public UserService() {

    }


    public void setUser(String userId, User user) {
        userMap.put(userId, user);
    }
    public void addUser(User user ) {
        userMap.put(user.getId(), user);
    }

    public Mono<User> getById(String userId) {

        User user = userMap.getOrDefault(userId, DEFAULT_USER);

        return Mono.just(user);
    }

    public Flux<User> findUserList(){

        return Flux.fromArray(userMap.values().toArray(new User[0]));

    }
}
