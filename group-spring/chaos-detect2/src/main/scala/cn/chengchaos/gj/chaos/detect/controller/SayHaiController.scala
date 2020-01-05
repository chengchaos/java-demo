package cn.chengchaos.gj.chaos.detect.controller

import java.nio.charset.StandardCharsets
import java.util.{Collections, Optional}

import com.alibaba.fastjson.JSONObject
import grizzled.slf4j.Logger
import javax.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod, RequestEntity}
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RestController}
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import scala.util.parsing.json.JSON

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]E470 - 2019/10/31 16:24 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@RestController
class SayHaiController(@Autowired @Qualifier(value = "loadBalanced") restTemplate: RestTemplate) {

  val logger: Logger = Logger(classOf[SayHaiController])

  @GetMapping(value = Array("/v1/say-hai/hello"))
  def sayHello(): String = {


    logger.info(s"restTemplate == $restTemplate")

    val map: java.util.Map[String, Object] = Collections.singletonMap("message", "Hello")
    new JSONObject(map).toJSONString
  }

  @GetMapping(value = Array("/v1/say-hai/hello2"))
  def sayHello2(): String = {

    logger.info(s"restTemplate == $restTemplate")

    val entity = this.restTemplate
      .getForEntity("http://CHAOS-DETECT/chaos-detect/v1/say-hai/hello", classOf[String])
    entity.getBody
  }

  @PostMapping(value= Array("/v1/run-detect"))
  def runDetect(request: HttpServletRequest, @RequestBody body: java.util.Map[String, Object]) : String = {

    val json = new JSONObject(body)

    val url = Optional.ofNullable(json.getString("url")).orElse(StringUtils.EMPTY)

    val uri = UriComponentsBuilder.fromUriString(url)
      .build()
      .encode(StandardCharsets.UTF_8)
      .toUri

    val method = json.getString("method")
    val httpMethod = if (method != null) {
      val methodName  = method.toString.toUpperCase
      methodName match {
        case "POST" => HttpMethod.POST
        case "DELETE" => HttpMethod.DELETE
        case "PUT" => HttpMethod.PUT
        case _ => HttpMethod.GET
      }
    } else {
      HttpMethod.GET
    }



    val bodyObj = json.getJSONObject("body")

    val requestBody  = if (bodyObj != null) {
      bodyObj.toJSONString
    } else {
      ""
    }

    logger.info(s"Request URI -=> ${uri}")
    logger.info(s"Request Method -=> ${httpMethod}")
    logger.info(s"Request Body -=> $bodyObj")

    val httpHeaders = new HttpHeaders();
    val names = request.getHeaderNames
    while (names.hasMoreElements) {
      val headName = names.nextElement()
      val headValue = request.getHeader(headName)
      logger.info(s"Request Headers -=> $headName : $headValue")
      httpHeaders.add(headName, headValue)
    }

    val requestEntity: HttpEntity[String] = new HttpEntity[String](requestBody, httpHeaders)

    try {
      val beginTime = System.currentTimeMillis()
      val result = this.restTemplate.exchange(uri, httpMethod, requestEntity, classOf[String])
      val endTime = System.currentTimeMillis()

      logger.info(s"执行时间: ${endTime - beginTime}")
      logger.info(s"结果: $result")

      result.getBody
    } catch {
      case e: Exception => e.getMessage
    }

  }
}
