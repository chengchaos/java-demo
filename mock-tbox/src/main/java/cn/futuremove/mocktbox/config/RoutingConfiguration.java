package cn.futuremove.mocktbox.config;

import cn.futuremove.mocktbox.webhandler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/17 0017 下午 5:47 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(@Autowired UserHandler userHandler) {

        return RouterFunctions.route(RequestPredicates.GET("/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getUserList)
                .andRoute(RequestPredicates.GET("/users/{userId}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
                .andRoute(RequestPredicates.DELETE("/users/{userId}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser);
    }
}
