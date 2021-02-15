package luxe.chaos.webflux.demo.service;

import luxe.chaos.webflux.demo.enties.User;
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
 * @author Cheng, Chao - 2/4/2021 11:52 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@ApiServer("http://127.0.0.1:8888")
public interface UserServiceApi {

    @GetMapping(value = "/v1/users")
    Flux<User> getAllUsers();

    @GetMapping(value = "/v1/users/{id}")
    Mono<User> getUserById(@PathVariable("id") String id);

    @DeleteMapping(value = "/v1/users/{id}")
    Mono<Void> deleteById(@PathVariable("id") String id);

    @PostMapping("/v1/users")
    Mono<User> createUser(@RequestBody Mono<User> user);

}
