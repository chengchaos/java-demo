package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

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
public class DemoPublish {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoPublish.class);

    private static final String publishTopic = "device/JavaSample2";


    private static MqttClient start(String broker, String clientId, String username, String password) throws Exception {

        int qos = 1;
        final MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());


        new Thread(() -> {

            try {

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setUserName(username);
                connOpts.setPassword(password.toCharArray());
                connOpts.setCleanSession(true);
                sampleClient.connect(connOpts);

                LOGGER.info("连接到 {} 成功", broker);

                int i = 0;
                for (; ; ) {
                    String content = "Message from MqttPublishSample ... " + (i++);
                    LOGGER.info("Publish {} -=> {}", publishTopic, content);
                    MqttMessage message = new MqttMessage(content.getBytes(StandardCharsets.UTF_8));
                    message.setQos(qos);
                    sampleClient.publish(publishTopic, message);
                    TimeUnit.SECONDS.sleep(3L);
                }

            } catch (MqttException me) {
                LOGGER.info("reason ==> {}", me.getReasonCode());
                LOGGER.info("msg ==> {}", me.getMessage());
                LOGGER.info("loc ==> {}", me.getLocalizedMessage());

                LOGGER.error("MqttException", me);
            } catch (Exception e) {
                LOGGER.error("MqttException", e);
            }

            LOGGER.info("Disconnected");
        }).start();

        return sampleClient;
    }


    public static void main(String[] args) throws Exception {

//        String broker = "tcp://v2x-admin.guojinauto.com:1883";
        String broker = "tcp://47.114.98.28:1883";

        String clientId = "JavaSample1";
        String username = "FMT-CE71807130051";
//        username = "12345678910000001";;
        String password = "aFqF0tFh";

        try {
            start(broker, clientId, username, password);
            LOGGER.info(".....");

        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException", e);
            Thread.currentThread().interrupt();
        }

    }


}