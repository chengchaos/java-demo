package com.example.my.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <strong>
 *     Jedis 使用连接池演示
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/2/23 0023 下午 5:46 <br />
 * @since 1.1.0
 */
public class JedisPoolDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisPoolDemo.class);

    public static void main(String[] args) {

        // Creating a JedisPool of jedis connections



        // Get a jedis connection form pool
        // 从 JedisPool 中获取的连接在使用完后必须通过调用
        // close() 方法返回到连接池中.
        //
        try (
                JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), Constants.REDIS_HOST);
                Jedis jedis = jedisPool.getResource()) {

            String restaurantName = "Kyoto ramen";
            Map<String, String> restaurantInfo = new HashMap<>();
            restaurantInfo.put("address", "801 Mission St, San Jose, CA");
            restaurantInfo.put("phone", "555-123-6543");

            jedis.hmset(restaurantName, restaurantInfo);
            jedis.hset(restaurantName, "rating", "5.0");

            String rating = jedis.hget(restaurantName, "rating");

            LOGGER.info("{} rating: {}", restaurantName, rating);

            LOGGER.info(("===="));
            // Print out hash
            for (Map.Entry<String, String> entry : jedis.hgetAll(restaurantName).entrySet()) {
                LOGGER.info("{}: {}", entry.getKey(), entry.getValue());
            }
        }


    }
}
