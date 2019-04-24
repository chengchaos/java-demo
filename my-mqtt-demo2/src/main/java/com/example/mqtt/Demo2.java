package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/18 0018 上午 10:59 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class Demo2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo2.class);


    public static void run() {

        try {
            /*
             * 第 1 步： 创建 MqttConnectOptions 实例
             */
            final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            mqttConnectOptions.setKeepAliveInterval(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);

            /*
             * 第 2 步： 创建 MqttClientPersistence 实例
             *
             * Paho 提供可插拔的持久化机制允许客户端存储消息。
             * 这里我们工作再 QoS 级别为 0， 因此我们使用内存储存，保持代码简单。
             *
             * 其他的持久化机制类都实现了 MqttClientPersistence 接口
             */
            MqttClientPersistence memoryPersistence = new MemoryPersistence();

            final String mqttServerHost = "localhost";
            final int mqttServerPort = 1883;
            final String mqttServerURI = String.format("tcp://$s:$d", mqttServerHost, mqttServerPort);

            final String mqttClientId = MqttAsyncClient.generateClientId();

            LOGGER.info("mqttClientId ==> {}", mqttClientId);

            /*
             * 第 3 步： MqttAsyncClient 实例
             */
            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(
                    mqttServerURI,
                    mqttClientId,
                    memoryPersistence
            );
            final String defTopic = "chengchao/drone01/def";

            /*
             * 第 4 步： 设置回调
             */
            mqttAsyncClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    LOGGER.error("", cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    LOGGER.info("messageArrived topic ==> {}", topic);
                    if (!topic.equals(defTopic)) {
                        return;
                    }
                    String messageText = new String(message.getPayload(), "UTF-8");
                    LOGGER.info("Topic: {}. Payload: {}", topic, messageText);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });

            /*
             * 第 5 步： 连接
             */
            IMqttToken mqttConnectToken = mqttAsyncClient.connect(
                    mqttConnectOptions,
                    null, // userContext
                    new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            org.eclipse.paho.client.mqttv3.MqttToken mqttToken;
                            LOGGER.info("Successfully connected");
                            LOGGER.info("connect callback's asyncActionToken ==> {}", asyncActionToken);
                            LOGGER.info("asyncActionToken ==> {}", asyncActionToken.getClass());
                            try {
                                IMqttToken subscribeToken = mqttAsyncClient.subscribe(
                                        defTopic, 0,
                                        null,
                                        new IMqttActionListener() {
                                            @Override
                                            public void onSuccess(IMqttToken asyncActionToken) {
                                                LOGGER.info("subscribe callback's asyncActionToken ==> {}", asyncActionToken);
                                                LOGGER.info("asyncActionToken ==> {}", asyncActionToken.getClass());
                                                LOGGER.info("Subscribed to the {} topic with QoS: {}", defTopic,
                                                        asyncActionToken.getGrantedQos()[0]);
                                            }

                                            @Override
                                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                                LOGGER.error("", exception);
                                            }
                                        }
                                );

                                LOGGER.info("subscribeToken ==> {}", subscribeToken);

                            } catch (Exception e) {
                                LOGGER.error("", e);
                            }
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                            LOGGER.error("", exception);
                        }
                    }
            );

            LOGGER.info("mqttConnectToken ==> {}", mqttConnectToken);
            boolean isComplete = mqttConnectToken.isComplete();

            LOGGER.info("iscomplete ==> {}", isComplete);

        } catch (MqttException me) {
            LOGGER.error("", me);
        }
    }

    public static void main(String[] args) {
        run();
    }
}
