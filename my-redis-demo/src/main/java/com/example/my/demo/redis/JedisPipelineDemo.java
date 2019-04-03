package com.example.my.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * <p>
 * <strong>
 * 在 jedis 中使用管道 (Pipeline)
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/2/23 0023 下午 5:09 <br />
 * @since 1.1.0
 */
public class JedisPipelineDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisPipelineDemo.class);

    public static void main(String[] args) {

        Jedis jedis = new Jedis(Constants.REDIS_HOST);

        // Create a Pipeline .
        Pipeline pipeline = jedis.pipelined();

        // Add commands to pipeline .
        pipeline.set("mykey", "myvalue");
        pipeline.sadd("myset", "value1", "value2");
        Response<String> stringValue = pipeline.get("mykey");
        Response<Long> noElementsInSet = pipeline.scard("myset");

        // Send command
        pipeline.sync();

        // Handle response
        LOGGER.info("mykey : {}", stringValue.get());
        LOGGER.info("Number of Elements in set: {}", noElementsInSet.get());

    }
}
