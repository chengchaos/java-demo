package cn.springcloud.book.ch2.filter

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.cloud.gateway.filter.{GatewayFilterChain, GlobalFilter}
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
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
 * @author Cheng, Chao - 1/5/2021 11:22 AM <br />
 */
@Component
class AuthSignatureFilter extends GlobalFilter with org.springframework.core.Ordered {

  private val logger: Logger = LoggerFactory.getLogger(classOf[AuthSignatureFilter])

  override def filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono[Void] = {
    val token = exchange.getRequest.getQueryParams.getFirst("authToken")
    logger.info(s"token => $token")

    if (token == null || token.isEmpty) {
      val response = exchange.getResponse
      val status = HttpStatus.UNAUTHORIZED
      logger.info(s"Http Status => $status")
      response.setStatusCode(status)
      response.setComplete()
    } else {
      chain.filter(exchange)
    }
  }

  override def getOrder: Int = -400
}
