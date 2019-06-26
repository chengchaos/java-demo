package com.example.myscala002.controller


import java.util
import java.util.Date

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{GetMapping, RestController}

import scala.collection.JavaConverters._

@RestController
class SayHello {


  private val logger = LoggerFactory.getLogger(classOf[SayHello])

  /**
    *
    * model.put("time", new Date());
    * model.put("message", "hello 程超");
    * @param name
    * @return
    */
  @GetMapping(value = Array("/v1/say-hello") )
  def hello(name: String) : java.util.Map[String, Object] = {

    logger.info("input name ==> {}", name);
    val result = if (name != null) name else "world"

    import collection.JavaConverters._

    val javaMap:java.util.Map[String, Object] = new util.HashMap[String, Object]()

    javaMap.put("time", new Date())
    javaMap.put("message", s"hello $result")
    javaMap
  }

}
