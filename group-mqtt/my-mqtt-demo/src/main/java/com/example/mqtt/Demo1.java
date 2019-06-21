package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/10 0010 下午 1:34 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class Demo1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo1.class);


    public static void main(String[] args) {
        String topic = "MQTT Examples";
        String content = "Message from MqttPublishSample";
        int qos = 2;

        String broker = "tcp://localhost:1883";

        String clientId = "JavaSample";

        MemoryPersistence persistence = new MemoryPersistence();


        try {

            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            LOGGER.info("Connecting to broker : {}", broker);

            sampleClient.connect(connOpts);

            LOGGER.info("Connected");
            LOGGER.info("Publish message: {}", content);
            MqttMessage message = new MqttMessage(content.getBytes(StandardCharsets.UTF_8));
            message.setQos(qos);
            sampleClient.publish(topic, message);

            LOGGER.info("Message published");

            sampleClient.disconnect();
            LOGGER.info("Disconnected");
        } catch (MqttException me) {
            LOGGER.info("reason ==> {}", me.getReasonCode());
            LOGGER.info("msg ==> {}", me.getMessage());
            LOGGER.info("loc ==> {}", me.getLocalizedMessage());

            LOGGER.error("", me);
        }

    }
}
