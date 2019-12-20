package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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

    private static final String mqttServerHost = "tcp://v2x-admin.guojinauto.com";
    private static final int mqttServerPort = 1883;
    private static final String mqttClientId = "JavaSample2";
    private static final String defTopic = "MQTT-Examples"; //"device/" + mqttClientId;

    public static MqttAsyncClient run() {


        try {
            /*
             * 第 1 步： 创建 MqttConnectOptions 实例
             */
            final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            mqttConnectOptions.setKeepAliveInterval(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);
            mqttConnectOptions.setUserName("10000042");
            mqttConnectOptions.setPassword("cLc5T39K".toCharArray());
            /*
             * 第 2 步： 创建 MqttClientPersistence 实例
             *
             * Paho 提供可插拔的持久化机制允许客户端存储消息。
             * 这里我们工作再 QoS 级别为 0， 因此我们使用内存储存，保持代码简单。
             *
             * 其他的持久化机制类都实现了 MqttClientPersistence 接口
             */
            MqttClientPersistence memoryPersistence = new MemoryPersistence();


            final String mqttServerURI = String.format("tcp://$s:$d", mqttServerHost, mqttServerPort);

            // final String mqttClientId = MqttAsyncClient.generateClientId()

            LOGGER.info("mqttClientId ==> {}", mqttClientId);

            /*
             * 第 3 步： MqttAsyncClient 实例
             */
            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(
                    mqttServerURI,
                    mqttClientId,
                    memoryPersistence
            );


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
//                    if (!topic.equals(defTopic)) {
//                        return;
//                    }
                    String messageText = new String(message.getPayload(), "UTF-8");
                    LOGGER.info("Topic: {}. Payload: {}", topic, messageText);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    LOGGER.info("token -=> {}", token);
                }
            });

            IMqttActionListener subscribeListenner = new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    LOGGER.info("subscribe callback's asyncActionToken ==> {}", asyncActionToken);
                    LOGGER.info("asyncActionToken ==> {}", asyncActionToken.getClass());
                    String[] topics = asyncActionToken.getTopics();

                    Stream.of(topics).forEach(topic ->
                            LOGGER.info("Subscribed to the {} topic with QoS: {}", topic, asyncActionToken.getGrantedQos()[0]));
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    LOGGER.error("", exception);
                }
            };


            IMqttActionListener connListener = new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    String subscribeTopic = "device/JavaSample2";
                    String[] topics = asyncActionToken.getTopics();
                    if (topics != null) {
                        Stream.of(topics).forEach(topic -> LOGGER.info("connListener ... topic : {}", topics));
                    }
                    try {
                        IMqttToken subscribeToken = mqttAsyncClient.subscribe(
                                subscribeTopic, 0,
                                null,
                                subscribeListenner);

                        LOGGER.info("订阅 ==> {}, subscribeToken -=>{}", subscribeTopic, subscribeToken);

                        LOGGER.info("连接成功");

                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    LOGGER.error("", exception);
                }
            };

            /*
             * 第 5 步： 连接
             */
            IMqttToken mqttConnectToken = mqttAsyncClient.connect(
                    mqttConnectOptions,
                    null, // userContext
                    connListener);

            LOGGER.info("mqttConnectToken ==> {}", mqttConnectToken);
            boolean isComplete = mqttConnectToken.isComplete();

            LOGGER.info("iscomplete ==> {}", isComplete);
            return mqttAsyncClient;
        } catch (MqttException me) {
            LOGGER.error("", me);
        }

        return null;
    }

    public static void main(String[] args) {
        MqttAsyncClient mqttAsyncClient = run();

        try {
            TimeUnit.SECONDS.sleep(2L);
            if (mqttAsyncClient != null) {
                boolean connected = mqttAsyncClient.isConnected();
                LOGGER.info(" mqttAsyncClient.isConnected() ? -=> {}", connected);

//                for (int i = 0; i < 1000; i++) {
//                    mqttAsyncClient.publish(defTopic, new MqttMessage(LocalDateTime.now().toString().getBytes()));
//                    TimeUnit.SECONDS.sleep(2L);
//                }
                mqttAsyncClient.disconnect(5L);
                mqttAsyncClient.close();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}
