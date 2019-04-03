package com.example.my.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/2/23 0023 下午 5:02 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class JedisSingleDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(JedisSingleDemo.class);

    public static void main(String[] args) {

        // 连接到 Redis Server
        Jedis jedis = new Jedis(Constants.REDIS_HOST);

        // String Operations
        String restaurant = "Extreme Pizza";
        jedis.set(restaurant, "300 BroadMay, New York, NY");
        jedis.append(restaurant, " 10011");
        String address = jedis.get("Extreme Pizza");
        LOGGER.info("Address for {} is {}", restaurant, address);

        // List Operations
        String listKey = "favorite_restaurants";
        jedis.lpush(listKey, "PF Chang's, New York, NY");
        jedis.rpush(listKey, "Outback Steakhouse", "Red Lobster");
        List<String> favoriteRestaurants = jedis.lrange(listKey, 0, -1);
        LOGGER.info("Favorite Restaurants: {}", favoriteRestaurants);


    }
}
