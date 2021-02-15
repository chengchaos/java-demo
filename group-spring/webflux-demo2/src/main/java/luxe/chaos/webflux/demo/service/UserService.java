package luxe.chaos.webflux.demo.service;

import luxe.chaos.webflux.demo.enties.User;
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
public interface UserService {

    Mono<User> add(User user);

    Flux<User> findAll();

    Mono<Void> deleteById(String id);

    Mono<Void> delete(User user);

    Mono<User> findById(String id);

    Mono<User> update(User user);

    Flux<User> saveAll(Mono<User> userMono);
}
