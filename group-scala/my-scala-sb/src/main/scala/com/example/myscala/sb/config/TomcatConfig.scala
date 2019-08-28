package com.example.myscala.sb.config

import grizzled.slf4j.Logger
import org.apache.catalina.connector.Connector
import org.apache.catalina.core.AprLifecycleListener
import org.apache.coyote.AbstractProtocol
import org.apache.coyote.http11.{Http11AprProtocol, Http11Nio2Protocol, Http11NioProtocol}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.embedded.tomcat.{TomcatConnectorCustomizer, TomcatServletWebServerFactory}
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment

/**
  * <p>
  * <strong>
  * 在 spring boot 2 中自定义 tomcat 的配置
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/7/4 18:21 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@Configuration
class TomcatConfig(@Autowired environment: Environment) {

  private val logger: Logger = Logger(classOf[TomcatConfig])

  @Bean
  def webServerFactory(): ConfigurableServletWebServerFactory = {

    val aprLifecycle: AprLifecycleListener = new AprLifecycleListener()
    aprLifecycle.setUseAprConnector(true)

    val aprConnector: Boolean = AprLifecycleListener.isAprAvailable && AprLifecycleListener.getUseAprConnector

    val protocolHandlerClassName = if (aprConnector) "org.apache.coyote.http11.Http11AprProtocol"
    else "org.apache.coyote.http11.Http11Nio2Protocol"

    val factory = new TomcatServletWebServerFactory()
    factory.addContextLifecycleListeners(aprLifecycle)
    factory.setProtocol(protocolHandlerClassName)

    val customizer: TomcatConnectorCustomizer = new TomcatConnectorCustomizer() {
      override def customize(connector: Connector): Unit = {
        connector.getProtocolHandler match {
          case protocol: Http11AprProtocol =>
            logger.info("protocol: Http11AprProtocol")
            setProperties(protocol)
          case protocol: Http11Nio2Protocol =>
            logger.info("protocol: Http11Nio2Protocol")
            setProperties(protocol)
          case protocol: Http11NioProtocol =>
            logger.info("protocol: Http11NioProtocol")

            setProperties(protocol)
        }
      }
      def setProperties[A](protocol: AbstractProtocol[A]) : Unit = {

        val connectTimeout= environment.getProperty("project.tomcat.connection-timeout", classOf[Int], 10000)
        val maxConnections= environment.getProperty("project.tomcat.max-connections", classOf[Int], 4096)
        val maxThreads= environment.getProperty("project.tomcat.max-threads", classOf[Int], 512)
        val acceptCount= environment.getProperty("project.tomcat.accept-count", classOf[Int], 256)
        val minSpareThreads= environment.getProperty("project.tomcat.min-spare-threads", classOf[Int], 128)


        protocol.setConnectionTimeout(connectTimeout) // 连接超时，单位ms
        protocol.setMaxConnections(maxConnections) // 最大连接数
        protocol.setMaxThreads(maxThreads) // 最大线程数
        protocol.setAcceptCount(acceptCount)  // 最大排队数
        protocol.setMinSpareThreads(minSpareThreads) // 最小监听线程

        logger.info(s"""连接超时 $connectTimeout ms, 最大连接数 $maxConnections, 最大线程数  $maxThreads, 最大排队数 $acceptCount, 最小监听线程 $minSpareThreads""")

      }
    }
    factory.addConnectorCustomizers(customizer)

    factory
  }


}

