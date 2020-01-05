package cn.chengchaos.gj.chaos.detect.global

import java.time.LocalDateTime

import grizzled.slf4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/23 0023 下午 2:01 <br />
  * @see cn.futuremove.tsp.tbox.config.ServerInfo
  * @since 1.1.0
  */
@Component
class SchedulingHelper() {

  val logger: Logger = Logger(classOf[SchedulingHelper])

  @Scheduled(fixedRate = 10 * 60 * 1000)
  def refreshServerInfo() : Unit = {
    val dt = LocalDateTime.now()
    logger.info(s"now -=> ${dt}")
  }
}
