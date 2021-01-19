package cn.springcloud.book.dao.impl;

import cn.springcloud.book.beans.User;
import cn.springcloud.book.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 11:22 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User getUser(String username, String password) {

        Criteria criteria = Criteria.where("user_name").is(username)
                .and("password").is(password);
        Query query = Query.query(criteria);
        return this.mongoTemplate.findOne(query, User.class);
    }

    @Override
    public long addUser(User user) {
        User saved = this.mongoTemplate.save(user);
        return saved.getUserId();
    }
}
