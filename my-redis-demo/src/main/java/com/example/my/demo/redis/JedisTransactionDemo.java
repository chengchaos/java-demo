package com.example.my.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.Set;

/**
 * <p>
 * <strong>
 * 演示 Jedis 的事物功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/2/23 0023 下午 5:17 <br />
 * @since 1.1.0
 */
public class JedisTransactionDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisTransactionDemo.class);

    public static void main(String[] args) {

        Jedis jedis = new Jedis(Constants.REDIS_HOST);

        // Initialize
        String user = "user:1000";
        String restaurantOrderCount = "restaurant_orders:200";
        String restaurantUsers = "restaurant_users:200";

        jedis.set(restaurantOrderCount, "400");
        jedis.sadd(restaurantUsers, "user:302", "user:401");

        // Create a Redis transaction
        Transaction transaction = jedis.multi();

        Response<Long> countResponse = transaction.incr(restaurantOrderCount);
        transaction.sadd(restaurantUsers, user);
        Response<Set<String>> userSet = transaction.smembers(restaurantUsers);

        // Execute transaction
        transaction.exec();

        // Handle response
        LOGGER.info("Number of orders: {}", countResponse.get());
        LOGGER.info("users: {}", userSet.get());


    }
}
