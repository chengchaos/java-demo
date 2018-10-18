package com.example.mytimer.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, String> redisTemplate;

    public void save() {
        LOGGER.info("save");
        redisTemplate.boundValueOps("chengchao").set("好人");

    }

    public String read() {
        Object o = redisTemplate.boundValueOps("chengchao").get();
        return Objects.nonNull(o) ? o.toString() : "null";
    }
}
