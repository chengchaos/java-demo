package com.example.myscala002.controller

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation.{GetMapping, RestController}

@RestController
class SayHai {

  private val logger = LoggerFactory.getLogger(classOf[SayHai])

  /**
    *
    * @param name
    * @return
    */
  @GetMapping(value=Array("/v1/hai"))
  def hai(name: String) :String = {

    val result = if (name != null) name else "world"

    s"hai $result"
  }


}
