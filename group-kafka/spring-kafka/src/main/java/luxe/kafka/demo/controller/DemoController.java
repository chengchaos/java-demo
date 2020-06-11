package luxe.kafka.demo.controller;

import luxe.kafka.demo.kafka.MyConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.print.DocFlavor;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/22 11:01 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);


    @GetMapping("/v1/say-hello")
    public Map<String, String> sayHello() {

        return Collections.singletonMap("message", "hello world");
    }

    private MyConsumer consumer;

    @PostMapping("/v1/start")
    public Map<String, String> start() {


        String brokers = "192.168.88.44:9092";
        String topic = "HelloKafkaTopic";
        String groupIdConfig = "chengchao-test";

        consumer = new MyConsumer(brokers, topic, groupIdConfig);
        consumer.start();

        return Collections.singletonMap("message", "success");
    }


    @PostMapping("/v1/stop")
    public Map<String, String> stop() {

        if (consumer == null) {
            return Collections.singletonMap("message", "on inst");
        }

        String result = String.valueOf(consumer.stop());
        return Collections.singletonMap("message", result);

    }
}
