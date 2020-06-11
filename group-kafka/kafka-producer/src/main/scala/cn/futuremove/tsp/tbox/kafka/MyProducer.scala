package cn.futuremove.tsp.tbox.kafka

import java.time.LocalDateTime
import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.kafka.clients.producer._

import scala.annotation.tailrec

/**
 * <p>
 * <strong>
 * 一个 Kafka 生产者的示例
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 1:47 <br />
 * @since 1.1.0
 */
class MyProducer(brokers: String, topic: String) extends LazyLogging {


  import scala.collection.JavaConversions._

  private lazy val producer: KafkaProducer[String, String] =
    new KafkaProducer[String, String](createConfig)


  private var executorService: ExecutorService = _

  @volatile
  private var stop: Boolean = false

  /**
   * Kafka 生产者有 3 个必须按属性：
   * bootstrap.servers (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)
   * 指定 borker 的地址清单， 地址格式为 host:port .
   * 清单里面不需要包含所有的 broker 地址，生产者会从给定的 broker 里找到
   * 其他的 broker 信息。
   *
   * key.serializer (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)
   * broker 希望接收到的消息的 key 和 value 都是字节数组。
   * 可以把一个对象发给 kafka， 但是生产者需要知道如何把对象转换成字节数组。
   *   key.serializer 必须设置为一个实现了 `org.apache.kafka.common.serialization.Serializer`
   * 接口的类。Kafka 也提供了 ByteArraySerializer / StringSerializer / IntegerSerializer
   * value.serializer (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)
   *
   * @return
   */
  private def createConfig: Map[String, Object] = {
    Map(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> classOf[org.apache.kafka.common.serialization.StringSerializer],
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> classOf[org.apache.kafka.common.serialization.StringSerializer],
      // 必须有多少分区副本收到消息，生产者猜确认为是写入成功了。
      // 0： 不会等； 1：首领节点收到；all: 所有参与复制的节点收到
      ProducerConfig.ACKS_CONFIG -> "all",
      // 生产者可以重发消息的次数
      ProducerConfig.RETRIES_CONFIG -> Int.box(0),
      ProducerConfig.BATCH_SIZE_CONFIG -> Int.box(16384),
      ProducerConfig.LINGER_MS_CONFIG -> Int.box(1),
      // 设置生产者内核缓冲区的大小
      ProducerConfig.BUFFER_MEMORY_CONFIG -> Long.box(33554432),
      // 默认不会压缩消息，改参数可以设置为 snappy, gzip, lz4
      // snappy：很平均； gzip： cpu 高压缩比高
      ProducerConfig.COMPRESSION_TYPE_CONFIG -> "none"
    )
  }


  //  implicit def block2Runnable(block: => Unit): Runnable = {
  //    new Runnable {
  //      override def run(): Unit = block
  //    }
  //  }


  def execute(): Unit = {

    import cn.futuremove.tsp.tbox._

    this.executorService = Executors.newSingleThreadExecutor()

    @tailrec
    def exec(id: Int): Unit = {
      if (!stop) {
        val message: String = "Message: " + LocalDateTime.now() + "_" + id
        val record = new ProducerRecord[String, String](topic, message)
        producer.send(record, new Callback() {
          override def onCompletion(recordMetadata: RecordMetadata, e: Exception): Unit = {
            val offset: Long = recordMetadata.offset()
            logger.info("send ==> {}, offset -=> {}", message, offset.toString);
          }
        })
        logger.info("send ==> {}", message)
        TimeUnit.MILLISECONDS.sleep(2000L)
        exec(id + 1)
      }
    }

    this.executorService.execute(exec(1))

  }


  def shutdown(): Unit = {
    this.stop = true
    this.producer.close()

    if (this.executorService != null) {
      executorService.shutdown()
    }
  }

}
