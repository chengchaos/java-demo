package com.study.cache.redis;

import com.study.cache.redis.pojo.User;
import com.study.cache.redis.utils.ByteUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTests {
    @Test
    public void test0() {
        // java客户端示例。 jedis初学者友好，操作和控制台类似
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("Charset=utf8");

        String great = "world";
        String setStatus = jedis.set("hello".getBytes(), great.getBytes());
        // set status => OK
        System.out.printf("set status => %s\n", setStatus);

        String result = jedis.get("hello"); // get key
        System.out.println(result);

        // pool
        JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig<Jedis>(),
                "127.0.0.1", 6379, 2000,
                "Charset=utf8", 0);

        int count = 0;
        long begin = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            try (Jedis res = jedisPool.getResource()) {
                if (res.get("hello") != null) {
                    count += 1;
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("耗时 1： " + (end - begin) / 1000.0);


        count = 0;
        begin = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            if (jedis.get("hello") != null) {
                count += 1;
            }

        }
        end = System.currentTimeMillis();

        System.out.println("耗时 2： " + (end - begin) / 1000.0);

        System.out.printf("count => %d\n", count);

        // controller -- 用户关注
        // 更新某个用户的粉丝数量
        // u10001 ---> 1000
        jedis.incr("u10001"); // incr

        User user = new User();
        user.setUid("u0001");
        user.setUname("程超");
        user.setAge(75);
        user.setImg("http://www.chaos.luxe/images/chengchao.jpg");

        jedis.hset(user.getUid(), "name", user.getUname());
//        jedis.hset(user.getUid(), "age", String.valueOf(user.getAge()));
        jedis.hset(user.getUid(), "img", user.getImg());

        jedis.hset(user.getUid().getBytes(), "age".getBytes(), ByteUtil.int2Bytes(user.getAge()));

        String age = jedis.hget(user.getUid(), "age");

        byte[] ageAsBytes = jedis.hget(user.getUid().getBytes(), "age".getBytes());
        System.out.println("age = "+ ByteUtil.bytes2Int(ageAsBytes));


    }
}
