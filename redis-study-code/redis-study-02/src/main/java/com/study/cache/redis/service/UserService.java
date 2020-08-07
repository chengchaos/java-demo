package com.study.cache.redis.service;

import com.study.cache.redis.annotations.NeteaseCache;
import com.study.cache.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate; // spring提供jdbc一个工具（mybastis类似）

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 根据ID查询用户信息 (redis缓存，用户信息以json字符串格式存在(序列化))
     */
    @Cacheable(value="user", key = "#userId")// 返回值 存到redis： value -- key: user::10001
    public User findUserById(String userId) {
        System.out.println("执行了数据库查询");
        String sql = "select * from tb_user_base where uid=?";
        User user = jdbcTemplate.queryForObject(sql, new String[]{userId}, new BeanPropertyRowMapper<>(User.class));
        return user;
    }

    @CacheEvict(value="user", key = "#user.uid") // 方法执行结束，清除缓存
    public void updateUser(User user) {
        String sql = "update tb_user_base set uname = ? where uid=?";
        jdbcTemplate.update(sql, new String[]{user.getUname(), user.getUid()});
    }

    /**
     * 根据ID查询用户名称
     */
    // 我自己实现一个类似的注解
    @NeteaseCache(value = "uname", key = "#userId") // 缓存
    public String findUserNameById(String userId) {
        // 查询数据库
        String sql = "select uname from tb_user_base where uid=?";
        String uname = jdbcTemplate.queryForObject(sql, new String[]{userId}, String.class);

        return uname;
    }
}
