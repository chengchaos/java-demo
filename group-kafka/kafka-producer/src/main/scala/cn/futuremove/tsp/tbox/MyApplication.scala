package cn.futuremove.tsp.tbox

import cn.futuremove.tsp.tbox.kafka.MyProducer
import com.typesafe.scalalogging.slf4j.LazyLogging
import javax.annotation.PreDestroy
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

import scala.collection.mutable.ListBuffer

@SpringBootApplication
@ComponentScan(basePackages = Array("cn.futuremove.tsp.tbox"))
class MyApplication

object MyApplication extends LazyLogging {

  type Sweeper = () => Unit

  private val sweeperBuffer: ListBuffer[Sweeper] = ListBuffer[Sweeper]()

  @PreDestroy
  def releaseResources(): Unit = {
    MyApplication.logger.warn("System shutdown ... now")
    sweeperBuffer.toList.foreach(callback => callback())
  }

  def register(callback: Sweeper): Unit = {
    callback +=: sweeperBuffer
  }

  def main(args: Array[String]): Unit = {

    SpringApplication.run(classOf[MyApplication], args: _*)

    val groupId = "group01"
    val numberOfThread = 1

    val brokers = "192.168.88.156:9092"

    val topic = "HelloKafkaTopic"

    val myProducer = new MyProducer(brokers, topic)

    myProducer.execute()
//    Thread.sleep(100000000)
//    myProducer.shutdown()
  }
}