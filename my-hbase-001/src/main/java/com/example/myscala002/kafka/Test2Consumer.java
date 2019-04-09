package com.example.myscala002.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/29 0029 下午 10:22 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class Test2Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test2Consumer.class);


    static Properties consumerConfig() {

        Properties props = new Properties();

        // "bootstrap.servers"
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.4:9092,192.168.1.4:9093");
        // group.id
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "chaos");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");

        // partition.assignment.strategy
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,"org.apache.kafka.clients.consumer.RoundRobinAssignor");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public void run() {

        try (
//                StringDeserializer stringDeserializer = new StringDeserializer()
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerConfig())
        ) {

            consumer.subscribe(Collections.singleton("test2"));


            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record :  records) {

                    if (LOGGER.isInfoEnabled()) {

                        LOGGER.info("topic = {}, partition = {}, offset = {} customer = {}, country = {}",
                                record.topic(),
                                record.partition(),
                                record.offset(),
                                record.key(),
                                record.value());
                    }
                }

            }
        }
    }
}
