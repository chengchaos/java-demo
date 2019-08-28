package cn.chengchaos.demo.httpclient

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/23 11:18 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@SpringBootApplication
class HttpClientDemoApp {

}

object HttpClientDemoApp {

  def main(args: Array[String]): Unit = {

    SpringApplication.run(classOf[HttpClientDemoApp], args: _*)
  }
}

