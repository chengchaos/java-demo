package cn.chengchaos.demo.httpclient.controller

import grizzled.slf4j.Logger
import org.junit.Test

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/9/5 17:11 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class SomeTest {



  private val logger: Logger = Logger(classOf[SomeTest])


  @Test
  def test001: Unit = {

    for (i <- 1 to 10) {
      println(s"i = $i")
    }

    for (i <- 1 until 10) {
      println(s"i = $i")
    }

    for (i <- Range(1, 10)) {
      println(s">>> i = $i")
    }

    for (i <- Range.inclusive(1, 10)) {
      println(s"=== i = $i")
    }
  }

}
