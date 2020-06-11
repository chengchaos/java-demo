package luxe.chaos.springmongodemo.repos;

import luxe.chaos.springmongodemo.entity.User;
import org.bson.Document;

import java.util.Optional;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/23 17:20 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public interface UserRepository {


    void saveUser(User user);

    Optional<Document> findById(String id);

    User findByName(String name);

    long update(User user);

    void delete(String userId);

}
