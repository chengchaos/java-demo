package cn.chengchaos.gj.chaos.detect

import grizzled.slf4j.Logger
import javax.annotation.PreDestroy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{ApplicationArguments, ApplicationRunner, CommandLineRunner, SpringApplication}
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

import scala.collection.mutable.ListBuffer

/**
 * EnableScheduling :
 * {{{ cn.futuremove.tsp.tbox.global.ScheduledHelper }}}
 *
 * 可视化监控参考: https://zhuanlan.zhihu.com/p/31575491
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = Array("cn.chengchaos.gj.chaos.detect"))
@EnableDiscoveryClient
class ChaosDetectApplication extends CommandLineRunner with ApplicationRunner {

  override def run(args: String*): Unit = {}

  override def run(args: ApplicationArguments): Unit = {}
}

object ChaosDetectApplication {

  val logger: Logger = Logger(classOf[ChaosDetectApplication])

  type MyClear = () => Unit

  private val callbackList: ListBuffer[MyClear] = ListBuffer[MyClear]()

  @PreDestroy
  def preDestroy(): Unit = {
    val begin = System.nanoTime()
    logger info "==== System shutdown ... now!"
    callbackList.foreach(callback => callback())
    logger.info(msg = {
      val exp = (System.nanoTime() - begin) / 1000000.0D
      s">>>> 关闭完成 ... 耗时 $exp 毫秒"
    })
  }

  def register(callback: => Unit): Unit = (() => callback) +=: callbackList


  def main(args: Array[String]): Unit = {

    SpringApplication.run(classOf[ChaosDetectApplication], args: _*)

  }


}