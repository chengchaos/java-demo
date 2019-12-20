package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class DemoSubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoSubscribe.class);

    private static MqttClient start(String broker, String clientId, String username, String password) throws MqttException {

        String subscribeTopic = "device/JavaSample2";

        LOGGER.debug("Connecting to broker : {}", broker);
        final MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        connOpts.setCleanSession(true);

        final MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
        sampleClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectionLost(Throwable cause) {
                LOGGER.error("connectionLost", cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                LOGGER.info("message arrived : topic -> {}, message -> {}", topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                LOGGER.info("deliveryComplete: {}", token);
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                LOGGER.info("connectComplete -=> reconnect: {}, serverURI: {}", reconnect, serverURI);
            }
        });



        new Thread(() -> {
            try {
                sampleClient.connect(connOpts);
                sampleClient.subscribe(subscribeTopic);
            } catch (MqttException me) {
                LOGGER.info("reason ==> {}", me.getReasonCode());
                LOGGER.info("msg ==> {}", me.getMessage());
                LOGGER.info("loc ==> {}", me.getLocalizedMessage());

                LOGGER.error("", me);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }).start();

        return sampleClient;

    }

    public static void main(String[] args) throws MqttException {

        //org.eclipse.paho.client.mqttv3.logging.LoggerFactory.setLogger("org.slf4j.Logger");

        //"tcp://v2x-admin.guojinauto.com:1883"
        String broker = "tcp://v2x-admin.guojinauto.com:1883";
        String clientId = "JavaSample2";
        String username = "chengchaos-666666";
        String whoCanSee = "cLc5T39K";

        try {

            start(broker, clientId, username, whoCanSee);
            TimeUnit.DAYS.sleep(Long.MAX_VALUE);
        } catch (InterruptedException ie) {
            LOGGER.error("InterruptedException", ie);
            Thread.currentThread().interrupt();
        }

//        sampleClient.disconnect();
        LOGGER.info("Disconnected");
    }
}
