package cn.futuremove.mocktbox.webhandler;

import cn.futuremove.mocktbox.entity.User;
import cn.futuremove.mocktbox.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/17 0017 下午 5:50 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Component
public class UserHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);


    @Autowired
    UserService userService;

    public Mono<ServerResponse> getUserList(ServerRequest request) {

        Flux<User> userFlux = userService.findUserList();
        userFlux.subscribe(user -> LOGGER.info(user.toString()));
        return ServerResponse.ok()
                .body(userFlux, User.class);

    }

    public Mono<ServerResponse> getUser(ServerRequest request) {

        String userId = request.pathVariable("userId");
        Mono<User> userMono = userService.getById(userId);
        userMono.subscribe(user -> LOGGER.info("user ==> {}", user.getId()));

        return ServerResponse.ok()
                .body(userMono, User.class);

    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {

        return Mono.empty();
    }
}
