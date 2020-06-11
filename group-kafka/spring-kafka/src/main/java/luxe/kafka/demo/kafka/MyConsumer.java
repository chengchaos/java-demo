package luxe.kafka.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/21 19:44 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class MyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyConsumer.class);


    /**
     * "auto.offset.reset";
     */
    public static final String LATEST = "latest";

    /**
     * "auto.offset.reset";
     */
    public static final String EARLIEST = "earliest";

    private volatile boolean stop;
    private ExecutorService executorService;

    private final String brokerAddress;
    private final String topic;
    private final String groupIdConfig;


    public MyConsumer(String brokers, String topic, String groupIdConfig) {

        this.brokerAddress = brokers;
        this.topic = topic;
        this.groupIdConfig = groupIdConfig;
        this.stop = false;
    }

    private Map<String, Object> createConfig() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.brokerAddress);
        configMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        configMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupIdConfig);
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, MyConsumer.LATEST);

        return configMap;
    }

    public void start() {
        this.executorService = Executors.newSingleThreadExecutor();


        executorService.execute(() -> {
            KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(createConfig());
            kafkaConsumer.subscribe(Collections.singletonList(topic));
            while (!this.stop) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(120));
                for (ConsumerRecord<String, String> record : records) {
                    LOGGER.info("offset = {}, value = {}", record.offset(), record.value());
                }
            }

            LOGGER.info(">>> thread end ...........");
            kafkaConsumer.close(Duration.ofMinutes(5L));
            LOGGER.info(">>> kafka consumer closed ...........");
        });
    }

    public boolean stop() {

        if (this.executorService == null) {
            return false;
        }

        this.stop = true;
        this.executorService.shutdown();

        try {
            this.executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOGGER.error("", e);
            Thread.currentThread().interrupt();
        }

        return this.executorService.isTerminated();
    }


}
