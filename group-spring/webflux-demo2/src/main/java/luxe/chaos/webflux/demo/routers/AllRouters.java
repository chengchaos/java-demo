package luxe.chaos.webflux.demo.routers;

import luxe.chaos.webflux.demo.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/4/2021 5:36 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return nest(path("/v1/"),
                route(GET("/users"), userHandler::gatAllUsers)
                        .andRoute(POST("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::createUsers)
                        .andRoute(DELETE("/users/{id}"), userHandler::deleteUsers)
                        .andRoute(GET("/users/{id}"), userHandler::getUserById)
        );
    }
}
