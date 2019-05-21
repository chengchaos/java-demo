package com.example.demo.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/26 0026 下午 4:46 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Component
@RabbitListener(queues = {"spring.#"})
public class FanoutReceiver {


    @RabbitHandler
    public void process(String hello) {
        System.err.println("Receiver : "+ hello);
    }
}
