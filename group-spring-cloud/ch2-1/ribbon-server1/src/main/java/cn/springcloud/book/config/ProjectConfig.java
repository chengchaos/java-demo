package cn.springcloud.book.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/5/2021 8:25 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@Configuration
public class ProjectConfig {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProjectConfig.class);

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        logger.info("instance RestTemplate ............");
        return new RestTemplate();
    }

    public feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }
}
