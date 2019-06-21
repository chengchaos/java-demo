package cn.futuremove.tsp.tbox.kafka

import java.time.LocalDateTime
import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/**
  * <p>
  * <strong>
  *   一个 Kafka 生产者的示例
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 1:47 <br />
  * @since 1.1.0
  */
class MyProducer(brokers: String, topic: String) extends LazyLogging {


  import scala.collection.JavaConversions._

  private lazy val producer : KafkaProducer[String, String] =
    new KafkaProducer[String, String](createConfig)

  //private val holder = new ListBuffer[ExecutorService]

  private var executorService : ExecutorService = _

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



//  implicit def block2Runnable(block: => Unit): Runnable = {
//    new Runnable {
//      override def run(): Unit = block
//    }
//  }


  def execute() : Unit = {

    import cn.futuremove.tsp.tbox._

    this.executorService = Executors.newSingleThreadExecutor()

    @tailrec
    def exec(id: Int) : Unit = {
      if (!stop) {
        val message:String = "Message: "+  LocalDateTime.now() + "_" + id
        producer.send(new ProducerRecord[String, String](topic, message))
        logger.info("send ==> {}", message)
        TimeUnit.MILLISECONDS.sleep(5000L)
        exec(id + 1)
      }
    }

    this.executorService.execute(exec(1))

//    holder += executor
  }


  def shutdown(): Unit = {
    this.stop = true
    this.producer.close()

    if (this.executorService != null) {
      executorService.shutdown()
    }
  }

}
