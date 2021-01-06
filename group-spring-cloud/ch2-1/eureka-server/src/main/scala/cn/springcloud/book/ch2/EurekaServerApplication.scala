package cn.springcloud.book.ch2

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @since 1.0
 * @see [相关类]
 * @author Cheng, Chao - 1/4/2021 2:31 PM <br />
 */
@SpringBootApplication
@EnableEurekaServer
class EurekaServerApplication
object EurekaServerApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[EurekaServerApplication], args:_*)
  }
}
