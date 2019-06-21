package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/18 15:56 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@SpringBootApplication
@ComponentScan(Array("cn.futuremove.tsp.uni.config.dbs"))
class DemoApp

object DemoApp {

  def main(args: Array[String]): Unit = {

    val ctx = SpringApplication.run(classOf[DemoApp], args: _*)

    println("ctx = "+ ctx)
  }
}
