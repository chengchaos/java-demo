package com.study.cache.redis.service;

import com.study.cache.redis.annotations.NeteaseCache;
import com.study.cache.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Service // 默认 单实例
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate; // spring提供jdbc一个工具（mybastis类似）

    @Autowired
    RedisTemplate redisTemplate;

    static Semaphore semaphore = new Semaphore(50); // 令牌 --- 单独的课程 juc

    /**
     * 根据ID查询用户信息 (redis缓存，用户信息以json字符串格式存在(序列化))
     */
    // @Cacheable(value="user", key = "#userId")// 返回值 存到redis： value -- key: user::10001
    public User findUserById(String userId) throws Exception {
        // 1. 先读取缓存
        Object cacheValue = redisTemplate.opsForValue().get(userId);
        if (cacheValue != null) {
            System.out.println("###从缓存读取数据");
            return (User) cacheValue;
        }
        // 2000 + 2W 并发 --- http --->tomcat --->thread --->controller/service
        // semaphore.acquire(); // 多线程争取 获取令牌。令牌没有了，等待---类似lock
        // 等待 --- 卡住 --- 设计结合业务需要 产品 --- 容错降级 / 不一定等待
        boolean result = semaphore.tryAcquire(1000L, TimeUnit.MILLISECONDS);// 等待三秒
        if(!result) {
            return null;
        }
        try {
            // 2. 没有缓存读取数据 --- 连接，并发 支撑非常小 ---
            String sql = "select * from tb_user_base where uid=?";
            User user = jdbcTemplate.queryForObject(sql, new String[]{userId}, new BeanPropertyRowMapper<>(User.class));
            System.out.println("***从数据库读取数据");
            // 3. 设置缓存
            redisTemplate.opsForValue().set(userId, user);
            return user;
        }finally {
            semaphore.release();
        }
    }

    @CacheEvict(value = "user", key = "#user.uid") // 方法执行结束，清除缓存
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
