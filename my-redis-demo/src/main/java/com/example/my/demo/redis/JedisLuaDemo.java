package com.example.my.demo.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import scala.util.parsing.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * <strong>
 *     在 Jedis 中运行 Lua 脚本
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/2/23 0023 下午 5:23 <br />
 * @since 1.1.0
 */
public class JedisLuaDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisLuaDemo.class);

    public static void main(String[] args) {



        String user = "users:id:992452";
        Map<String, Object> data = new HashMap<>();

        data.put("name", "Tina");
        data.put("sex", "female");
        data.put("grade", "A");



        // Register Lua script:
        try (
                Jedis jedis = new Jedis(Constants.REDIS_HOST);
                InputStream luaInputStream = JedisLuaDemo.class
                .getClassLoader()
                .getResourceAsStream("update-json.lua");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(luaInputStream))
             ;
        ) {

            jedis.set(user, JSON.toJSONString(data));


            String luaScript = bufferedReader.lines()
                    .collect(Collectors.joining("\n"));
            String luaSHA = jedis.scriptLoad(luaScript);

            // Eval Lua script
            List<String> luaKeys = Collections.singletonList(user);
            List<String> luaArgs = Collections.singletonList("{\"grade\":\"C\"}");

            jedis.evalsha(luaSHA, luaKeys, luaArgs);

            LOGGER.info("{}: {}", user, jedis.get(user));

        } catch (IOException e) {
            LOGGER.error("", e);
        }




    }
}
