package cn.futuremove.tsp.tbox.kafka

import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 1:47 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class MyProducer(brokers: String, topic: String) extends LazyLogging {

  private val holder = new ListBuffer[ExecutorService]

  @volatile
  private var stop: Boolean = false

  private def createConfig : Map[String, Object] = {
    Map(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
      ProducerConfig.ACKS_CONFIG -> "all",
      ProducerConfig.RETRIES_CONFIG -> Int.box(0),
      ProducerConfig.BATCH_SIZE_CONFIG -> Int.box(16384),
      ProducerConfig.LINGER_MS_CONFIG -> Int.box(1),
      ProducerConfig.BUFFER_MEMORY_CONFIG -> Long.box(33554432),
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> classOf[org.apache.kafka.common.serialization.StringSerializer],
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> classOf[org.apache.kafka.common.serialization.StringSerializer]
    )
  }

  import scala.collection.JavaConversions._
  private val producer : KafkaProducer[String, String] = new KafkaProducer[String, String](createConfig)


  implicit def block2Runnable(block: => Unit): Runnable = {
    new Runnable {
      override def run(): Unit = block
    }
  }

  def execute() : Unit = {
    val executor = Executors.newSingleThreadExecutor()

    @tailrec
    def exec(id: Int) : Unit = {
      if (!stop) {
        val messag = "Message "+ id
        producer.send(new ProducerRecord[String, String](topic, messag))
        TimeUnit.MILLISECONDS.sleep(100L)
        exec(id + 1)
      }
    }

    executor.execute({
      exec(1)
    })

    holder += executor
  }


  def shutdown(): Unit = {
    this.stop = true
    this.producer.close()
    if (holder.nonEmpty) {
      val executor = holder.head
      executor.shutdown()
    }
  }

}
