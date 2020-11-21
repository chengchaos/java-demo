package com.example.demo.service

import java.util
import java.util.concurrent.{ThreadLocalRandom, TimeUnit}
import java.util.stream.{Collectors, StreamSupport}

import org.springframework.stereotype.Service

import scala.collection.JavaConverters.asScalaBufferConverter

@Service
class DemoServiceImpl extends DemoService {

  override def getUserName: String = {

    new Thread(() => println("hello scala 12")).start()

    val timeout: Long = ThreadLocalRandom.current().nextLong(10)
    TimeUnit.SECONDS.sleep(timeout)

    val buffer = util.Arrays.asList("chengchao", "handong", "gaojiyuan")
      .stream()
      .collect(Collectors.toList())
      .asScala
    buffer.foreach( x => println(x))

    "chengchao"
  }

}
