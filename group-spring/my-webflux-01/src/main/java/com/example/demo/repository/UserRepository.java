package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2019-04-26 20:59 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public interface UserRepository extends
        ReactiveMongoRepository<User, String> {

}
