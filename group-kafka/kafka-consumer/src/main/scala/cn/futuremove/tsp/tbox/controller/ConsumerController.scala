package cn.futuremove.tsp.tbox.controller

import java.util.concurrent.{Callable, ConcurrentHashMap}

import cn.futuremove.tsp.tbox.kafka.MyConsumer
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.web.bind.annotation.{PathVariable, PostMapping, RestController}
import org.springframework.web.context.request.async.WebAsyncTask

import scala.collection.mutable

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/20 11:20 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@RestController
class ConsumerController extends LazyLogging {


  private val map: ConcurrentHashMap[String, MyConsumer] =
    new ConcurrentHashMap[String, MyConsumer]()

  val brokers = "192.168.88.156:9092"
  val topic = "HelloKafkaTopic"


  private def addToMap(id: String, consumer: MyConsumer) : String = {
    this.map.put(id, consumer)
    "OK"
  }


  @PostMapping(value=Array("/start/{id}"))
  def startConsumer(@PathVariable(value="id") id: String) : WebAsyncTask[String] = {

//    val groupId = "group01_"+ id
    val groupId = "group01"
    val numberOfThread = 1

    logger.info("group ID ==> {}", groupId)

    new WebAsyncTask[String](new Callable[String] {
      override def call(): String = {

        if (map.containsKey(id)) {
          "EXISTS"
        } else {
          val myConsumer = new MyConsumer(brokers, groupId, topic, id)
          myConsumer.execute(numberOfThread)
          addToMap(id, myConsumer)
        }
      }
    })

  }

  @PostMapping(value=Array("/stop/{id}"))
  def stopConsumer(@PathVariable(value="id") id: String) : String = {

    val consumer = map.remove(id)
    if (consumer != null) {
      consumer.shutdown()
    }
    "OK"
  }
}
