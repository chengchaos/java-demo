package cn.futuremove.tsp.kafka.consumer.service

import java.math.BigDecimal
import java.util.concurrent.{ExecutorService, Executors}

//import net.minidev.json.JSONObject
//import net.minidev.json.parser.JSONParser

import com.alibaba.fastjson.JSON
import scala.util.parsing.json.JSONObject

import grizzled.slf4j.Logger
import org.apache.commons.lang3.StringUtils
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.header.Headers
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/24 12:01 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@Service
class KafkaService {

  val logger: Logger = Logger(classOf[KafkaService])

  val executor: ExecutorService = Executors.newFixedThreadPool(4)

  /**
    * 车辆碰撞
    *
    * @param cr ConsumerRecord
    */
  @KafkaListener(topics = Array("collision-signal-status-data"))
  def packetCrashListener(cr: ConsumerRecord[String, String]): Unit = {

    val json = cr.value.trim

    if (StringUtils.isNoneBlank(json)) {
      this.executor.submit(new Runnable {
        override def run(): Unit = {
          printMessage(json)
        }
      })
    }

  }

  def printMessage(json: String): Unit = {

    val jsonObject = JSON.parseObject(json)

    val jsonStr = JSON.toJSONString(jsonObject, true)

    //val jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE)
    //val jsonObject = jsonParser.parse(json).asInstanceOf[JSONObject]

    //获得碰撞车辆的vin 时间 坐标 车速
    // java.math.BigDecimal cannot be cast to java.lang.String
    // String speed = (String) jsonObject.get("speed");
    val speed = jsonObject.getOrDefault("speed", "").toString
    val vin = jsonObject.get("code").asInstanceOf[String]

    val serverTimeOri = jsonObject.get("serverTime")
    val serverTimel = if (serverTimeOri != null) {
      java.lang.Long.parseLong(serverTimeOri.toString)
    } else {
      0L
    }

    val lat1 = jsonObject.get("lat").asInstanceOf[BigDecimal]
    val lat = lat1.doubleValue
    val lon1 = jsonObject.get("lon").asInstanceOf[BigDecimal]
    val lon = lon1.doubleValue


    logger.info(
      s"""碰撞 vin ==> $vin, 时间 ==> $serverTimel,
         |坐标： lat ==> $lat, lon ==> $lon, json ==> $jsonStr"""
        .stripMargin)


  }

  /**
    * 测试 Kafka 是否可以连通：
    *
    * {{{
    *   bin/kafka-console-producer.sh  --topic jing-jing --broker-list localhost:9092
    * }}}
    *
    * @param cr
    */
  @KafkaListener(topics = Array("jing-jing"))
  def kafkaTest(cr: ConsumerRecord[String, String]): Unit = {
    this.executor.submit(new Runnable {
      override def run(): Unit = {
        printlnJingjing(cr)
      }
    })
  }

  private def printlnJingjing(cr: ConsumerRecord[String, String]): Unit = {

    val key = cr.key()
    val value = cr.value()
    val headers: Headers = cr.headers()
    val offset: Long = cr.offset()
    val partition: Int = cr.partition()

    val headersArray = headers.toArray

    if (headersArray.nonEmpty) {
      headers.toArray.foreach(header => {
        val hk = header.key()
        val hv = header.value()
        logger.info(s"header key ==> $hk, header value ==> $hv")
      })
    }

    logger.info(s"接收到 value ==> $value, offset ==> $offset")

  }
}
