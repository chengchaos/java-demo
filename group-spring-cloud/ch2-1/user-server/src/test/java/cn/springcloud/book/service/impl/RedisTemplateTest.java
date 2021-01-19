package cn.springcloud.book.service.impl;

import cn.springcloud.book.UserServerApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 3:11 PM <br />
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RedisTemplateTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTemplateTest.class);

    private StringRedisTemplate redisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Test
    public void redisGetSetTest() {

        String key = "testkey";
        String value = "testValue";


        ValueOperations<String, String>  opsForValue = this.redisTemplate.opsForValue();
        opsForValue.set(key, value);

        BoundValueOperations<String, String> valueOps = this.redisTemplate.boundValueOps(key);
        Object o = valueOps.get();

        Assert.assertNotNull("Must be not null!", o);
        logger.info("o.getClass => {}", o.getClass());
        logger.info("o => {}", o.toString());


    }

}
