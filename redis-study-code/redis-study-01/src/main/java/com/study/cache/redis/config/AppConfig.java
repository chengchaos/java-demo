package com.study.cache.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

// 用这个类 代替了xml文件
@Configuration //
public class AppConfig {


    public Map<String, String> configs() {
        try (
                InputStream inputStream = this.getClass().
                        getClassLoader().getResourceAsStream("config.properties")) {

            Properties props = new Properties();
            props.load(inputStream);
            Map<String, String> result = new HashMap<>(props.size(), 1.0f);
            props.forEach((k, v) -> result.put(k.toString(), v.toString()));
            return result;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    // 创建对象，spring托管 <bean ...
    @Bean
    public JedisPool jedisPool() {

        Map<String, String> config = this.configs();

        System.out.printf("config => %s\n" , config);
        String host = config.get("redis_host");
        int port = Integer.parseInt(config.get("redis_port"), 10);
        int timeout = 2000;
        int database = 0;
        String auth = config.get("redis_password");

        return new JedisPool(new GenericObjectPoolConfig<Jedis>(), host, port, timeout, auth, database);

    }

}
