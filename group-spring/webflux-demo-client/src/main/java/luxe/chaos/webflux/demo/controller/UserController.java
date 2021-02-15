package luxe.chaos.webflux.demo.controller;

import luxe.chaos.webflux.demo.enties.User;
import luxe.chaos.webflux.demo.service.UserServiceApi;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 10:30 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class UserController {

    private final UserServiceApi userServiceApi;

    public UserController(UserServiceApi userServiceApi) {
        this.userServiceApi = userServiceApi;
    }


    @GetMapping(value = "/v1/users")
    public Flux<User> getAllUsers() {
        return this.userServiceApi.getAllUsers();
    }

    @GetMapping(value = "/v1/users/{id}")
    public Mono<User> getUserById(@PathVariable("id") String id) {
        return this.userServiceApi.getUserById(id);
    }

    @DeleteMapping(value = "/v1/users/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return this.userServiceApi.deleteById(id);
    }

    @PostMapping("/v1/users")
    public Mono<User> createUser(@RequestBody User user) {
        return this.userServiceApi.createUser(Mono.just(user));
    }
}
