package com.example.mystores.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

/**
 * <p>
 * <strong>
 * 简短的描述
 * </strong>
 * <br /><br />
 * 详细的说明
 * </p>
 *
 * @author chengchao - 18-11-22 下午1:53 <br />
 * @since 1.0
 */
public class Listener {

    public static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(topics = "chengchao", group = "sync-group")
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (!kafkaMessage.isPresent()) {
            return ;
        }

        String info = kafkaMessage.get().toString();

        LOGGER.info(">>> {}", info);
    }

}
