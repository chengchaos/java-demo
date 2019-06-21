package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/18 0018 下午 1:05 <br />
 * @since 1.1.0
 */
public class SensorsManager implements MqttCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorsManager.class);

    private static final String SENSOR_EARTH_HUMIDITY = "earthhumidity";
    private static final String SENSOR_SUNLIGHT = "sunlight";
    private static final String TOPIC_SEPARATOR = "/";

    private final String boardCommandsTopic;
    private final String boardDataBaseTopic;
    private final String encoding;
    private final MqttAsyncClient asyncClient;

    private final String earthHumidityTopic;
    private final String visibleLightTopic;
    private final String infraredLightTopic;
    private final String ultraVioletIndexTopic;

    private volatile boolean isSunlightSensorTurnedOn = false;
    private volatile boolean isEarthHumiditySensorTurnedOn = false;

    public SensorsManager(final MqttAsyncClient asyncClient,
                          final String boardCommandsTopic,
                          final String boardDataBaseTopic,
                          final String encoding) {
        this.boardCommandsTopic = boardCommandsTopic;
        this.boardDataBaseTopic = boardDataBaseTopic;
        this.encoding = encoding;
        this.asyncClient = asyncClient;

        /*
         * Build and save the topic names that we will use
         * to publish the data from the sensors
         */
        this.earthHumidityTopic = boardDataBaseTopic.concat(SENSOR_EARTH_HUMIDITY);

        final String sunlightDataBaseTopic = boardDataBaseTopic.concat(SENSOR_SUNLIGHT);
        this.visibleLightTopic = String.join(TOPIC_SEPARATOR, sunlightDataBaseTopic, "visiblelight");
        this.infraredLightTopic = String.join(TOPIC_SEPARATOR, sunlightDataBaseTopic, "ir");
        this.ultraVioletIndexTopic = String.join(TOPIC_SEPARATOR, sunlightDataBaseTopic, "uv");

    }


    /**
     *
     * @param topic topic
     * @param textForMessage Message
     * @param actionListener IMqttActionListener
     * @param qos QoS
     * @param retained 是否保持 retain
     * @return
     */
    public IMqttDeliveryToken publishMessage(final String topic,
                                             final String textForMessage,
                                             IMqttActionListener actionListener,
                                             final int qos,
                                             final boolean retained) {

        byte[] bytesForPayload;

        try {
            bytesForPayload = textForMessage.getBytes(this.encoding);
            return asyncClient.publish(topic, bytesForPayload, qos,
                    retained,
                    null,
                    actionListener);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }


    public void publishProcessedCommandMessage(final String sensorName,
                                               final String command) {
        final String topic = String.format("%s/%s", boardCommandsTopic,
                sensorName);
        final String textForMessage = String.
                format("%s successfully processed command: %s",
                        sensorName,
                        command);
        publishMessage(topic, textForMessage, null, 0, false);
    }


    public void loop() {

        if (isEarthHumiditySensorTurnedOn) {
            final int humidityLevel = ThreadLocalRandom
                    .current()
                    .nextInt(1, 101);
            publishMessage(earthHumidityTopic,
                    String.format("%d %%", humidityLevel),
                    null,
                    0,
                    false);
        }

        if (isSunlightSensorTurnedOn) {
            final int visibleLight = ThreadLocalRandom
                    .current()
                    .nextInt(201, 301);
            publishMessage(visibleLightTopic,
                    String.format("%d lm", visibleLight),
                    null,
                    0,
                    false);
        }

        final int infraredLight = ThreadLocalRandom
                .current()
                .nextInt(251, 281);
        publishMessage(infraredLightTopic,
                String.format("%d lm", infraredLight),
                null,
                0,
                false);

        final int ultraVioletIndex = ThreadLocalRandom
                .current()
                .nextInt(0, 16);
        publishMessage(ultraVioletIndexTopic,
                String.format("%d UV Index", ultraVioletIndex),
                null,
                0,
                false);
    }


    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.error("", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        String messageText = new String(message.getPayload(), this.encoding);
        LOGGER.info("Topic: {}. Playlod: {}", topic, messageText);
        if (!topic.startsWith(boardCommandsTopic)) {
            // the topic for the arrived message doesnt' start with boardCommadsTopic
            return;
        }

        final boolean isTurnOnMessage = messageText.equals("TURN ON");
        final boolean isTurnOffMessage = messageText.equals("TURN OFF");

        boolean isInvalidCommand = false;
        boolean isInvalidTopic = false;

        String sensorName = topic.replaceFirst(boardCommandsTopic,"")
                .replaceFirst(TOPIC_SEPARATOR, "");
        switch (sensorName) {
            case SENSOR_SUNLIGHT:
                if (isTurnOnMessage) {
                    isSunlightSensorTurnedOn = true;
                } else if (isTurnOffMessage) {
                    isSunlightSensorTurnedOn = false;
                } else {
                    isInvalidCommand = true;
                }
                break;
            case SENSOR_EARTH_HUMIDITY:
                if (isTurnOnMessage) {
                    isEarthHumiditySensorTurnedOn = true;
                } else if (isTurnOffMessage) {
                    isEarthHumiditySensorTurnedOn = false;
                } else {
                    isInvalidCommand = true;
                }
                break;
            default:
                isInvalidTopic = true;
        }

        if (!isInvalidCommand && !isInvalidTopic) {
            publishProcessedCommandMessage(sensorName, messageText);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOGGER.info("deliveryComplete token ==> {}", token);
    }
}
