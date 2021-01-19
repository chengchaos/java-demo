package cn.springcloud.book.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 2:50 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private RedisConnectionFactory factory = null;

    public RedisConnectionFactory redisConnectionFactory() {

        logger.error("create RedisConnectionFactory ...");

        if (this.factory != null) {
            return this.factory;
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大空闲数
        poolConfig.setMaxIdle(30);
        // 最大连接数
        poolConfig.setMaxTotal(50);
        // 最大等待毫秒数
        poolConfig.setMaxWaitMillis(2000);


        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        rsc.setHostName("192.168.56.102");
        rsc.setPort(6379);
        rsc.setPassword("foobared");

        JedisConnectionFactory cf = new JedisConnectionFactory(rsc);

        // 获取单机的 Redis 配置
        this.factory = cf;

        return factory;
    }


    public RedisTemplate redisTemplate() {

        logger.error("create RedisTemplate ...");
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);

        //redisTemplate.setConnectionFactory(this.redisConnectionFactory());

        return redisTemplate;
    }
}
