package cn.futuremove.tsp.kafka.consumer

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/10 14:43 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@SpringBootApplication
@EnableScheduling
class KafkaConsumerApp

object KafkaConsumerApp extends LazyLogging {

  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[KafkaConsumerApp], args: _*)

  }
}
