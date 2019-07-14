package com.example.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/26 0026 下午 4:42 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Configuration
public class SimpleRabbitConfig {

    public Queue queue() {
        return new Queue("hello");
    }
}
