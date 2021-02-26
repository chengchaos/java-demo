package luxe.chaos.threads.demo001.controller

import luxe.chaos.threads.demo001.dt.DateTimeHelper
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation.{GetMapping, RestController}

import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @since 1.0
 * @see [相关类]
 * @author Cheng, Chao - 2/20/2021 3:23 PM <br />
 */
@RestController
class PingController {

  private val logger: Logger = LoggerFactory.getLogger(classOf[PingController])
  private val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  @GetMapping(value = Array("/v1/ping"))
  def ping() : String = {
    logger.info("Time of message => {}", dtf.format(DateTimeHelper.toLocalDateTime(new Date())))
    "Pong"
  }

}
