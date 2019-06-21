package com.example.mqtt2

import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

import grizzled.slf4j.Logger
import org.eclipse.paho.client.mqttv3.{IMqttActionListener, IMqttDeliveryToken, IMqttToken, MqttAsyncClient, MqttCallback, MqttConnectOptions, MqttMessage}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/18 18:42 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class MqttClient2(host: String, port: Int, topic: String) {

  private val logger: Logger = Logger(classOf[MqttClient2])

  var client: MqttAsyncClient = _

  val callback: MqttCallback = new MqttCallback {

    override def connectionLost(cause: Throwable): Unit = {
      logger.error("connection lost ...", cause)
    }

    override def messageArrived(topic: String, message: MqttMessage): Unit = {
      logger.info(msg = s"receive ==> $topic ==> $message")
    }

    override def deliveryComplete(token: IMqttDeliveryToken): Unit = {
      logger.info(msg = s"delivery complete token ==> $token")
    }
  }

  val actionListener: IMqttActionListener = new IMqttActionListener() {
    override def onSuccess(asyncActionToken: IMqttToken): Unit = {
      logger.info(s"connect success ==> $asyncActionToken")
      subscribe(client)
    }

    override def onFailure(asyncActionToken: IMqttToken, exception: Throwable): Unit = {
      logger.error(msg = "connect failure ", exception)
    }
  }

  def subscribe(client: MqttAsyncClient): Unit = {

    client.subscribe(topic, 0, null, new IMqttActionListener {
      override def onSuccess(asyncActionToken: IMqttToken): Unit = {

        logger.info("subscribe success")
      }

      override def onFailure(asyncActionToken: IMqttToken, exception: Throwable): Unit = {
        logger.error("subscribe failure", exception)
      }
    })
  }


  def sar[A](whatever: A)(setBlock: A => Unit): A = {
    setBlock(whatever)
    whatever
  }

  def conn(): Unit = {


    val option = sar(new MqttConnectOptions) {
      option => {
        option.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1)
        option.setKeepAliveInterval(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT)
        option.setUserName("chengchao")
        option.setPassword("over2012".toCharArray)
      }
    }

    val serverUri = s"tcp://$host:$port"
    val clientId = MqttAsyncClient.generateClientId()
    val persistence = new MemoryPersistence
    this.client = new MqttAsyncClient(serverUri, clientId, persistence)
    logger.info(s"client id ==> $clientId")

    client.setCallback(callback)

    client.connect(option, null, actionListener)

  }


  def write(info: String): Unit = {
    val message = new MqttMessage(info.getBytes(StandardCharsets.UTF_8))
    client.publish(topic, message)
  }
}

object MqttClient2 {

  def main(args: Array[String]): Unit = {
    val client2 = new MqttClient2("192.168.88.156", 1883, "topic")

    client2.conn()


    for (i <- 1 to 30) {
      client2.write("hello .. " + i)
      TimeUnit.SECONDS.sleep(5L)
    }

  }
}