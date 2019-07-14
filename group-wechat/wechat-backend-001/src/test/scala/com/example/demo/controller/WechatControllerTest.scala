package com.example.demo.controller

import com.alibaba.fastjson.JSON
import com.example.demo.DemoApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.junit4.SpringRunner

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/7/8 12:49 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@RunWith(classOf[SpringRunner])
@SpringBootTest(
  classes = Array(classOf[DemoApplication]),
  webEnvironment = WebEnvironment.DEFINED_PORT
)
class WechatControllerTest {



  @Test
  def parseJsonTest() :Unit = {
    val jsonStr = """{"access_token":"ACCESS_TOKEN","expires_in":7200}"""

    val json = JSON.parseObject(jsonStr)

    println(json.getString("access_token"))
    println(json.getInteger("expires_in"))


  }
}
