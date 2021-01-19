package cn.springcloud.book.service.impl;

import cn.springcloud.book.beans.User;
import cn.springcloud.book.dao.UserDao;
import cn.springcloud.book.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 11:18 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
//    @HystrixCommand(fallbackMethod="getUserFallback")
    public User getUser(String username, String password) {
        return this.userDao.getUser(username, password);
    }

    public User getUserFallback(String username, String password) {

        logger.warn("run in getUserFallback u => {}, p => {}",
                username,
                password);

        return User.NULL;
    }


    @Override
    public long addUser(User user) {
        return this.userDao.addUser(user);
    }
}
