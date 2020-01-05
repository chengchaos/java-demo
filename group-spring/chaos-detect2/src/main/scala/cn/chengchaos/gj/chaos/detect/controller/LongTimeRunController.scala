package cn.chengchaos.gj.chaos.detect.controller

import java.util.Collections
import java.util.concurrent.TimeUnit

import com.alibaba.fastjson.JSONObject
import grizzled.slf4j.Logger
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RestController}

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]E470 - 2019/11/7 18:33 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@RestController
class LongTimeRunController {

  private val logger: Logger = Logger(classOf[LongTimeRunController])

  @GetMapping(value = Array("/v1/long-time/{time}/run"))
  def execute(@PathVariable(name = "time") time: Long): String = {

    logger.info(s"time -=> $time")
    val realTime: Long = if (time > 1000) time
    else 1000L

    TimeUnit.MILLISECONDS.sleep(realTime);
    val map: java.util.Map[String, Object] = Collections.singletonMap("message", "time : "+ realTime)

    logger.info(s"return -=> $map")

    new JSONObject(map).toJSONString

  }

}
