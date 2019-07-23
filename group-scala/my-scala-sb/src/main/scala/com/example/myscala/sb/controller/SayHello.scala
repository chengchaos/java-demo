package com.example.myscala.sb.controller

import java.util
import java.util.Date
import java.util.concurrent.Callable

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RestController}
import org.springframework.web.context.request.async.WebAsyncTask

import scala.collection.JavaConverters._

@RestController
class SayHello(@Autowired executor: ThreadPoolTaskExecutor) {


  private val logger = LoggerFactory.getLogger(classOf[SayHello])

  /**
    *
    * model.put("time", new Date());
    * model.put("message", "hello 程超");
    * @param name
    * @return
    */
  @GetMapping(value = Array("/v1/say-hai/{name}") )
  def hello(@PathVariable(value="name") name: String) : WebAsyncTask[util.Map[String, String]] = {

    new WebAsyncTask[util.Map[String, String]](5000L, executor, new Callable[util.Map[String, String]] {
      override def call(): util.Map[String, String] = {
        util.Collections.singletonMap("hello", name)
      }
    })
  }

}
