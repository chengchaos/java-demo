package luxe.chaos.webflux.demo.repositories;

import luxe.chaos.webflux.demo.enties.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/4/2021 11:22 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
