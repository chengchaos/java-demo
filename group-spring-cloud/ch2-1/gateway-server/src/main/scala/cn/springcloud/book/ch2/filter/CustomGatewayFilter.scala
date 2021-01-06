package cn.springcloud.book.ch2.filter

import cn.springcloud.book.ch2.filter.CustomGatewayFilter.COUNT_START_TIME
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.cloud.gateway.filter.{GatewayFilter, GatewayFilterChain}
import org.springframework.web.server.ServerWebExchange
import org.springframework.core.Ordered
import reactor.core.publisher.Mono

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @since 1.0
 * @author Cheng, Chao - 1/5/2021 11:05 AM <br />
 */
object CustomGatewayFilter {
  val COUNT_START_TIME: String = "countStartTime"
}

class CustomGatewayFilter extends GatewayFilter with Ordered {

  private val logger: Logger = LoggerFactory.getLogger(classOf[CustomGatewayFilter])

  override def filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono[Void] = {
    val attributes = exchange.getAttributes
    logger.info(s"attributes => {}", attributes)
    attributes. put(COUNT_START_TIME, System.currentTimeMillis())
    chain.filter(exchange).`then`(
      Mono.fromRunnable(() => {
        val startTime: Long = exchange.getAttribute(COUNT_START_TIME)
        if (startTime != null) {
          val endTime: Long = (System.currentTimeMillis() - startTime)
          logger.info("{} => {}ms", exchange.getRequest.getURI.getRawPath,
            endTime)
        }
      })
    )
  }

  override def getOrder(): Int = Ordered.LOWEST_PRECEDENCE;


}
