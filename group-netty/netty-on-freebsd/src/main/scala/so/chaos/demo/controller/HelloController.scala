package so.chaos.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RestController}

import scala.collection.JavaConversions

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/14 0014 上午 10:52 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@RestController
class HelloController {

  import scala.collection.JavaConversions._
  import java.util

  @GetMapping(value = Array("/hai"))
  def sayHai(name: String) : util.Map[String, Object] = {
    val v:Option[String] = Option(name)
    Map("message" -> v.getOrElse("world"))
    //JavaConversions.mapAsJavaMap(m)
  }
}
