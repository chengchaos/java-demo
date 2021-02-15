package luxe.chaos.webflux.demo.handlers;

import luxe.chaos.webflux.demo.enties.User;
import luxe.chaos.webflux.demo.ex.CheckException;
import luxe.chaos.webflux.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/4/2021 5:07 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Component
public class UserHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> gatAllUsers(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_PROBLEM_JSON)
                .body(this.userService.findAll(), User.class);
    }

    public Mono<ServerResponse> createUsers(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.flatMap(u -> {
            logger.info("input User => {}", u);
            if (u.getAge() > 120) {
                CheckException ex = new CheckException();
                ex.setFieldName("出错");
                ex.setFieldValue("年纪不要大于 120");
                return Mono.error(ex);
            }
            this.userService.add(u);
            return ok()
                    .contentType(APPLICATION_PROBLEM_JSON)
                    .body(this.userService.add(u), User.class);
        });
//        return ok()
//                .contentType(APPLICATION_PROBLEM_JSON)
//                .body(this.userService.saveAll(userMono), User.class);
    }

    public Mono<ServerResponse> deleteUsers(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.userService.findById(id)
                .flatMap(u -> this.userService.update(u)
                        .then(ok().build()))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        logger.debug("request id => {}", id);
        return ok()
                .contentType(APPLICATION_PROBLEM_JSON)
                .body(this.userService.findById(id), User.class)
//                .switchIfEmpty(notFound().build())
                ;
    }
}
