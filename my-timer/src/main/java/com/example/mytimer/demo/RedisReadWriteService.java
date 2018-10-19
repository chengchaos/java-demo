package com.example.mytimer.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * <strong>
 * 简短的描述
 * </strong>
 * <br /><br />
 * 详细的说明
 * </p>
 *
 * @author chengchao - 18-10-18 下午7:26 <br />
 * @since 1.0
 */
@Service
public class RedisReadWriteService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReadWriteService.class);



    @Autowired
    private StringRedisTemplate redisTemplate;


    public void save() {
        LOGGER.info("redisTemplate -> {}", redisTemplate);
        redisTemplate.boundValueOps("chengchao").set("好人");


        String res = redisTemplate.execute((RedisCallback<String>) connection -> {
            Long size = connection.dbSize();
            // Can cast to StringRedisConnection if using a StringRedisTemplate
            StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
            System.err.println("stringRedisConnection: "+ stringRedisConnection);
            return stringRedisConnection.get("chengchao");
        });

        System.out.println(">>>>>>>>>" + res);
    }

    public String read() {
        LOGGER.info("redisTemplate -> {}", redisTemplate);
        Object o = redisTemplate.boundValueOps("chengchao2").get();
        return Objects.nonNull(o) ? o.toString() : "null";
    }
}