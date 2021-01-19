package cn.springcloud.book.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * <br />
 * 参考： https://zhuanlan.zhihu.com/p/109226599
 * </p>
 *
 * @author Cheng, Chao - 1/15/2021 5:39 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Configuration
public class MyCaffeineCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(MyCaffeineCacheConfig.class);

    @Bean
    public Cache<String, Object> caffeineCache() {
        logger.info("=== *** === 初始化 Caffeine");
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }
}
