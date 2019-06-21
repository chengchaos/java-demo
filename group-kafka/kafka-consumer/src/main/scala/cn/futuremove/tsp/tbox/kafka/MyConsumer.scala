package cn.futuremove.tsp.tbox.kafka

import java.time.Duration
import java.util
import java.util.concurrent.{ArrayBlockingQueue, ExecutorService, ThreadPoolExecutor, TimeUnit}

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}

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
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 12:07 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class MyConsumer(brokers: String, groupId: String, topic: String, taskId: String = "01") extends LazyLogging {


  @volatile
  private var closeFlag: Boolean = false


  private def createConfig(): Map[String, Object] = Map(
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
    ConsumerConfig.GROUP_ID_CONFIG -> groupId,
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> Boolean.box(true),
    ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG -> Int.box(1000),
    ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG -> Int.box(30000),
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "latest", // in("latest", "earliest", "none"),
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[org.apache.kafka.common.serialization.StringDeserializer],
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer"

  )

  import scala.collection.JavaConverters._

  private val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](createConfig().asJava)

  consumer.subscribe(util.Arrays.asList(topic))

  private val executorHolder = ListBuffer[ExecutorService]()

  implicit def block2Runnable(block: => Unit): Runnable = {
    new Runnable {
      override def run(): Unit = block
    }
  }

  def execute(numberOfThreads: Int): Unit = {
    val executor: ExecutorService = new ThreadPoolExecutor(numberOfThreads,
      numberOfThreads,
      0,
      TimeUnit.MICROSECONDS,
      new ArrayBlockingQueue[Runnable](1000),
      new ThreadPoolExecutor.CallerRunsPolicy())



    @tailrec
    def exec(): Unit = {
      val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(100))

      records.asScala
        .foreach(record => println("Process: " + record.value() +
          ", Offset: " + record.offset() +
          ", GroupID: "+ groupId +
          ", TaskID: "+ taskId +
          ", By ThreadID: " + Thread.currentThread().getId)
      )

      if (closeFlag) {
        logger.info(s"关闭 consumer: taskId ==> $taskId, groupId ==> $groupId")
        closeConsumer()
      }
      exec()
    }

    executor.submit(new Runnable {
      override def run(): Unit = {
        exec()
      }
    })
    executorHolder += executor
  }

  def shutdown(): Unit = {

    this.closeFlag = true
    //this.closeConsumer()

  }


  private def closeConsumer(): Unit = {
    try {
      consumer.close(Duration.ofSeconds(1L))
      if (executorHolder.nonEmpty) {
        val executor = executorHolder.head
        this.closeExecutor(executor)
      }
    } catch {
      case ex: Exception => logger.error("", ex)
    }
  }

  private def closeExecutor(executor: ExecutorService): Unit = {

    try {

      executor.shutdown()
      executor.awaitTermination(5000L, TimeUnit.MILLISECONDS)
    } catch {
      case ex: Exception => logger.error(", ex")
    }
  }
}
