package com.example.mytimer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 简短的描述
 * </strong>
 * <br /><br />
 * 详细的说明
 * </p>
 *
 * @author chengchao - 18-10-18 下午5:03 <br />
 * @since 1.0
 */
public class RedisConfig2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig2.class);

    /**
     * {@cde @Bean } 和 {@code @ConfigurationProperties}
     * 该功能在官方文档是没有提到的，
     * 我们可以把@ConfigurationProperties和@Bean和在一起使用。
     * 举个例子，我们需要用@Bean配置一个Config对象，Config对象有a，b，c成员变量需要配置，
     * 那么我们只要在yml或properties中定义了a=1,b=2,c=3，
     * 然后通过@ConfigurationProperties就能把值注入进Config对象中 * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();

        //DefaultJedisClientConfiguration jedisClientConfiguration = new DefaultJedisClientConfiguration()
        factory.setUsePool(true);
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        LOGGER.info("JedisConnectionFactory bean init success.");
        return factory;
    }

    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        JedisConnectionFactory factory = getConnectionFactory();
        LOGGER.info(this.host + "," + factory.getHostName() + "," + factory.getDatabase());
        LOGGER.info(this.password + "," + factory.getPassword());
        LOGGER.info("{}", factory.getPoolConfig().getMaxIdle());
        // factory.setHostName(this.host);
        // factory.setPassword(this.password);
        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
        return template;
    }





    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

    //    @Value("${spring.redis.cluster.nodes}")
    private List<String> clusterNodesList;


    // Redis服务器地址
    @Value("${spring.redis.host}")
    private String host;

    // Redis服务器连接端口
    @Value("${spring.redis.port}")
    private int port;

    // Redis服务器连接密码（默认为空）
    @Value("${spring.redis.password}")
    private String password;

    // 连接超时时间（毫秒）
    @Value("${spring.redis.timeout}")
    private int timeout;

    // Redis数据库索引（默认为0）
    @Value("${spring.redis.database}")
    private int database;

    // 连接池最大连接数（使用负值表示没有限制）
    @Value("${spring.redis.pool.max-active}")
    private int maxTotal;

    // 连接池最大阻塞等待时间（使用负值表示没有限制）
    @Value("${spring.redis.pool.max-wait}")
    private int maxWaitMillis;

    // 连接池中的最大空闲连接
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    // 连接池中的最小空闲连接
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;




    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {

        LOGGER.info("===> 2 XXX RedisConf jedisPoolConfig 初始化 JedisPoolConfig ===");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 连接池最大连接数（使用负值表示没有限制）
        jedisPoolConfig.setMaxTotal(this.maxTotal);
        // 连接池最大阻塞等待时间（使用负值表示没有限制）
        jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
        // 连接池中的最大空闲连接
        jedisPoolConfig.setMaxIdle(this.maxIdle);
        // 连接池中的最小空闲连接
        jedisPoolConfig.setMinIdle(this.minIdle);

        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnCreate(true);
        jedisPoolConfig.setTestWhileIdle(true);

        return jedisPoolConfig;
    }

    @Bean(name = "jedisClusterConfig")
    public RedisClusterConfiguration getClusterConfiguration() {

        LOGGER.debug("nodes: {}, timeout: {}, max-redirects: {}, ",
                clusterNodes, timeout, 5);

        Map<String, Object> source = new HashMap<>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeout", timeout);
        source.put("spring.redis.cluster.max-redirects", 5);

        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    @Bean(name = "jedisConnectionFactory")
    public RedisConnectionFactory jedisConnectionFactory(@Qualifier(value = "jedisClusterConfig") RedisClusterConfiguration redisClusterConfiguration) {

        LOGGER.info("===> 1. XXX RedisConf jedisConnectionFactory 初始化 jedisConnectionFactory");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration);


        jedisConnectionFactory.setHostName(this.host);
        jedisConnectionFactory.setPort(this.port);
        jedisConnectionFactory.setDatabase(this.database);
        jedisConnectionFactory.setTimeout(this.timeout);

        String host = jedisConnectionFactory.getHostName();

        int database = jedisConnectionFactory.getDatabase();
        int port = jedisConnectionFactory.getPort();
        int timeout = jedisConnectionFactory.getTimeout();

        LOGGER.debug("host: {}, port: {}, database: {}, timeout: {}",
                host, port, database, timeout);

        return jedisConnectionFactory;

    }


//    @Bean(name = "redisTemplate")
////    public RedisTemplate<String, String> functionDomainRedisTemplate(@Qualifier(value = "jedisConnectionFactory") RedisConnectionFactory factory) {
////        LOGGER.info("=== XXX RedisConf functionDomainRedisTemplate 初始化JedisPoolConfi ===");
////        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
////        redisTemplate.setConnectionFactory(factory);
////        redisTemplate.setKeySerializer(new StringRedisSerializer());
////        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
////        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
////        redisTemplate.setValueSerializer(new StringRedisSerializer());
////        redisTemplate.afterPropertiesSet();
////        redisTemplate.setEnableTransactionSupport(true);
////        return redisTemplate;
////    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(@Qualifier(value = "jedisConnectionFactory") RedisConnectionFactory connectionFactory) {

        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);

//        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(@Qualifier(value = "jedisConnectionFactory") RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }




}
