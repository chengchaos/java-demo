package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/26 0026 下午 4:57 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Configuration
public class FanoutRabbitConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutRabbitConfig.class);


    @Bean
    public Queue commandQueue() {
//        return new Queue("fanout.command");
        Queue nonDurableQueue = QueueBuilder.nonDurable()
                .autoDelete()
                .exclusive()
                .build();
        String name = nonDurableQueue.getName();
        LOGGER.info("queue name ==> {}", name);
        return nonDurableQueue;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingExchangeCommand(FanoutExchange fanoutExchange) {
        //return BindingBuilder.bind(commandQueue()).to(fanoutExchange)


        return BindingBuilder.bind(commandQueue()).to(fanoutExchange);

    }
}
