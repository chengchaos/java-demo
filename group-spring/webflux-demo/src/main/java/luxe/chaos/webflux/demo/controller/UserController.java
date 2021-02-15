package luxe.chaos.webflux.demo.controller;

import luxe.chaos.webflux.demo.enties.User;
import luxe.chaos.webflux.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * @author Cheng, Chao - 2/4/2021 11:24 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/v1/users")
    public Flux<User> getAll() {
        return this.userService.findAll();
    }

    @GetMapping(value = "/stream/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getAllStream() {
        return this.userService.findAll();
    }

    @PostMapping(value = "/v1/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<User> add(@RequestBody User user) {
        logger.info("user => {}", user);
        return this.userService.add(user);
    }

    @GetMapping(value = "/stream/users/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<User>> findOneStream(@PathVariable String id) {
        return this.userService.findById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/v1/users/{id}")
    public Mono<ResponseEntity<User>> findOne(@PathVariable String id) {
        return this.userService.findById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/v1/users/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        // this.userService.delete(id).subscribe(result -> logger.info("Done!"))
        // 只是转换时，使用 map
        // 操作数据并返回 Mono 时，使用 flatMap。
        return this.userService.findById(id)
                .flatMap(user -> this.userService.delete(user)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping(value = "/stream/users-up/{id}")
    public Mono<ResponseEntity<User>> update(@PathVariable final String id,
                                             @RequestBody(required = false) User user
    ) {
        logger.info("input id => {}", id);
        return this.userService.findById(id)
                .flatMap(u -> this.userService.update(u.setName(user.getName())
                        .setAge(user.getAge())))
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
