package cn.springcloud.book.ch2.config

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.{GatewayFilterSpec, PredicateSpec, RouteLocatorBuilder}

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @since 1.0
 * @see [相关类]
 * @author Cheng, Chao - 1/5/2021 8:44 AM <br />
 */
class ProjectConfigScala {

  private val logger: Logger = LoggerFactory.getLogger(classOf[ProjectConfigScala])

  def customRouteLocator(builder: RouteLocatorBuilder): RouteLocator = {

    logger.info(s"init .............. builder = $builder")

    builder.routes().
      route(
        (ps: PredicateSpec) => {
          ps.path("/ribbon-server-1/**").
            filters((f: GatewayFilterSpec) => f.rewritePath("/ribbon-server-1/(?<segment>.*)",
              "/$\\{segment}")).
            uri("http://192.168.56.1:18081/").
            id("ribbon-server-1")
        }).build()
  }
}
