package com.example.myscala002.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/27 0027 下午 11:17 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private static Properties kafkaProps = new Properties();


    public static void flushData(int seconds) {

        kafkaProps.put("bootstrap.servers", "192.168.1.4:9092,192.168.1.4:9093");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> producer =
                     new KafkaProducer<>(kafkaProps)) {
            ProducerRecord<String, String> record = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (int i = 0; i < seconds; i++) {
                String key = sdf.format(new Date());
                record = new ProducerRecord<>("test2", key, "France " + key);
                TimeUnit.SECONDS.sleep(1L);
                LOGGER.info("key ==> {}", key);
                producer.send(record);
            }
            TimeUnit.SECONDS.sleep(10L);
        } catch (InterruptedException e) {
            LOGGER.info("interrupted ... ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }

    public static void main(String[] args) {

        flushData(60 * 60);
    }


}
