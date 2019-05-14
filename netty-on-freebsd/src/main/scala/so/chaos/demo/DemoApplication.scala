package so.chaos.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import so.chaos.demo.server.EchoServer

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/14 0014 上午 10:50 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@SpringBootApplication
class DemoApplication


object DemoApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[DemoApplication], args:_*)

    EchoServer.getEchoServer.start()

  }
}
