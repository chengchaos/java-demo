package cn.chengchaos.demo.httpclient.controller

import java.nio.charset.StandardCharsets

import grizzled.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, RestController}
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@RestController
class SayHai {

//  private val logger = LoggerFactory.getLogger(classOf[SayHai])

  private val logger: Logger = Logger(classOf[SayHai])

  @Autowired
  private var restTemplate: RestTemplate =  _

  /**
    *
    * @param name
    * @return
    */
  @GetMapping(value=Array("/v1/hai"))
  def hai(name: String) :String = {

    logger.info("hai..")

    """{"message" : "hai" }"""
  }


  @GetMapping(value=Array("/v1/baidu"))
  def baidu(u: String) : String = {

    val begin = System.currentTimeMillis()

    val uri = UriComponentsBuilder.fromUriString(u)
      .encode(StandardCharsets.UTF_8)
      .build()
      .toUri

    val result =  this.restTemplate.getForEntity(uri, classOf[String])
      .getBody

    val end = System.currentTimeMillis()
    val esp = (end - begin) / 1000.0D

    logger.info(s"执行时间： $esp (秒)")

    result

  }

}
