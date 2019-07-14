package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * <p>
 * mongod --dbpath d:\mongodb\data --smallfiles
 * </p>
 *
 * @author chengchao - 2019-04-26 21:00 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@RestController
@RequestMapping("/uer")
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/stream/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getUsers() {

        return userRepository.findAll();
    }


    @GetMapping(value = "/")
    public Flux<User> getUsersStream() {

        return userRepository.findAll();
    }

    public Mono<ResponseEntity<Integer>> deleteUser(
            @PathVariable("id") String id
    ) {
        /*
         * 当要操纵数据,并返回一个 Mono 时, 使用 flatMap
         * 到哪不操纵数据, 只是转换数据时, 使用 map
         */
        return this.userRepository
                .findById(id)
                .flatMap(user -> this.userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity<>(1, HttpStatus.OK)))
                        .defaultIfEmpty(new ResponseEntity<>(0, HttpStatus.NOT_FOUND))
                );
    }
}
