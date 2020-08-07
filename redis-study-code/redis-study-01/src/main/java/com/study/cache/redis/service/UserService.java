package com.study.cache.redis.service;

import com.study.cache.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    JedisPool jedisPool;

    /**
     * 根据ID查询用户信息 (redis缓存，用户信息以json字符串格式存在(序列化))
     */
    public User findUserById(String userId) {
        User user = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 1. 查询Redis是否有数据 -- string方式
//            String result = jedis.get(userId); // get key
//            if(result != null && !"".equals(result) ) {
//                // TODO json字符串转换为User
//                user = JSONObject.parseObject(result, User.class); // 把缓存中的值，返回给你的用户
//                return user; // 命中 缓存
//            }

            // 1. 查询Redis是否有数据 -- hash的方式
            Map<String, String> result = jedis.hgetAll(userId); // hgetall
            if (result != null && result.size() > 0) {
                // TODO map对象转换为User
                user = new User();
                user.setAge(Integer.valueOf(result.get("age").toString()));
                user.setImg(result.get("img").toString());
                user.setUname(result.get("uname").toString());
                user.setUid(result.get("uid").toString());
                return user; // 命中 缓存
            }

            // 2. 查询数据库
            String sql = "select * from tb_user_base where uid=?";
            user = jdbcTemplate.queryForObject(sql, new String[]{userId}, new BeanPropertyRowMapper<>(User.class));

            // 3. 数据塞到redis中 // json格式，或者 hmset hset
            // String userJsonStr = JSONObject.toJSONString(user);
             HashMap<String,String> userInfo = new HashMap<>();
            userInfo.put("age", String.valueOf(user.getAge()));
            userInfo.put("uname", String.valueOf(user.getUname()));
            userInfo.put("uid", String.valueOf(user.getUid()));
            userInfo.put("img", String.valueOf(user.getImg()));
            jedis.hmset(userId,userInfo ); // jedis --- hmset key filed1 value1 ....
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return user;
    }

    /**
     * 根据ID查询用户名称
     */
    public String findUserNameById(String userId) {
        String uname = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 1. 查询Redis是否有数据
            uname = jedis.hget(userId, "uname");
            if (uname != null && !"".equals(uname)) {
                return uname; // 命中 缓存
            }

            // 2. 查询数据库
            // 查询数据库
            String sql = "select uname from tb_user_base where uid=?";
            uname = jdbcTemplate.queryForObject(sql, new String[]{userId}, String.class);

            // 3. 数据塞到redis中 -- 单独更新uname 对象一个属性
            jedis.hset(userId, "uname", uname);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return uname;
    }
}
