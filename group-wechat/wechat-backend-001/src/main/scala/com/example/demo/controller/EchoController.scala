package com.example.demo.controller

import java.util.Collections

import org.springframework.web.bind.annotation.{GetMapping, RestController}

import scala.collection.{JavaConversions, JavaConverters}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/7/8 10:52 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@RestController
class EchoController {


  @GetMapping(value=Array("/_echo/hai"))
  def sayHai() : java.util.Map[String, Object] = {

    Collections.singletonMap("message", "Hai...")

  }
}
