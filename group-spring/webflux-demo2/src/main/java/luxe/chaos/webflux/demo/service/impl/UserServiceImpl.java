package luxe.chaos.webflux.demo.service.impl;

import luxe.chaos.webflux.demo.enties.User;
import luxe.chaos.webflux.demo.repositories.UserRepository;
import luxe.chaos.webflux.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> add(User user) {
        return this.userRepository.insert(user);
    }

    @Override
    public Flux<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        logger.info("input id => {}", id);
        return this.userRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return this.userRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(User user) {
        return this.userRepository.delete(user);
    }

    @Override
    public Mono<User> update(User user) {
        logger.info("user => {}", user);
        return this.userRepository.save(user);
    }

    @Override
    public Flux<User> saveAll(Mono<User> userMono) {
        return this.userRepository.saveAll(userMono);
    }
}
