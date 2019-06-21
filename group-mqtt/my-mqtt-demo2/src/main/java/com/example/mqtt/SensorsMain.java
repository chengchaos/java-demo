package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/18 0018 下午 2:22 <br />
 * @since 1.1.0
 */
public class SensorsMain {


    private static final Logger LOGGER = LoggerFactory.getLogger(SensorsMain.class);

    private static final String BOARD_NAME = "location001";
    private static final String ENCODING_FOR_PAYLOAD = "UTF-8";


    public static void main(String[] args) {

        final String boardCommandsTopic = String
                .format("commands/boards/%s", BOARD_NAME);
        final String boardDataBaseTopic = String
                .format("data/boards/%s/", BOARD_NAME);
        final String boardStatusTopic = String
                .format("status/boards/%s", BOARD_NAME);
        final String commandsTopicFilter = String
                .format("%s/+", boardCommandsTopic);

        try {
            final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            mqttConnectOptions.setKeepAliveInterval(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);

            final String lastWillMessageText = "OFFLINE";
            byte[] bytesForPayload = lastWillMessageText.getBytes(StandardCharsets.UTF_8);

            mqttConnectOptions.setWill(boardStatusTopic, bytesForPayload, 2, true);

            MqttClientPersistence persistence = new MqttDefaultFilePersistence();

            final String mqttServerHost = "localhost";
            final int mqttServerPort = 1883;
            final String mqttServerURI = String
                    .format("tcp://%s:%d", mqttServerHost, mqttServerPort);


            final String mqttClientId = BOARD_NAME;

            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(
                    mqttServerURI,
                    mqttClientId,
                    persistence
            );


            SensorsManager sensorsManager = new SensorsManager(
                    mqttAsyncClient,
                    boardCommandsTopic,
                    boardDataBaseTopic,
                    ENCODING_FOR_PAYLOAD
            );

            mqttAsyncClient.setCallback(sensorsManager);

            IMqttToken mqttConnectToken = mqttAsyncClient.connect(
                    mqttConnectOptions,
                    null,
                    new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            LOGGER.info("Successfully connected");
                            try {

                                // In this case, we don't use token
                                IMqttToken subscribeToken = mqttAsyncClient.subscribe(
                                        commandsTopicFilter,
                                        2,
                                        null,
                                        new IMqttActionListener() {
                                            @Override
                                            public void onSuccess(IMqttToken asyncActionToken) {

                                                LOGGER.info("Subsribed to the {} topic with Qos: {}",
                                                        asyncActionToken.getTopics()[0],
                                                        asyncActionToken.getGrantedQos()[0]);
                                                // Publish a retained message indication the
                                                // board is online
                                                sensorsManager.publishMessage(boardStatusTopic,
                                                        "ONLINE", null, 2, true);
                                            }

                                            @Override
                                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                                                LOGGER.error("", exception);
                                            }
                                        }
                                );

                                LOGGER.info("subscribeToken ==> {}", subscribeToken.isComplete());
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


            LOGGER.info("mqttConnectToken ... {}", mqttConnectToken.isComplete());

            int index = 0;
            while ((index++) < Integer.MAX_VALUE) {
                sensorsManager.loop();
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (InterruptedException e) {
            LOGGER.warn("Sleep interruption {}", e.getMessage());
            Thread.currentThread().interrupt();

        } catch (Exception e) {
            LOGGER.error("", e);
        }


    }
}
