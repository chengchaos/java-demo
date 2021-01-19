package cn.springcloud.book.dao;

import cn.springcloud.book.beans.User;

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
public interface UserDao {

    User getUser(String username, String password);

    long addUser(User user);
}
