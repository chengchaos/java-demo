package cn.chengchaos.demo.httpclient.config

import grizzled.slf4j.Logger
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.beans.factory.annotation.{Qualifier, Value}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.client.{ClientHttpRequestFactory, HttpComponentsClientHttpRequestFactory}
import org.springframework.web.client.RestTemplate
import vip.chengchao.helper.scala.CodingHelper

import scala.beans.BeanProperty

/**
  * <p>
  * <strong>
  *   配置并实例化 RestTemplate
  * </strong><br /><br />
  * 如题。
  *
  * 参考链接： <br />
  * : https://blog.csdn.net/wsywb111/article/details/80311716 <br />
  * : https://www.cnblogs.com/zhangzhi19861216/p/8594674.html <br />
  * : https://www.cnblogs.com/kingszelda/p/8988505.html <br />
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/23 14:36 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@Configuration
class RestTemplateConfig {


  private val logger: Logger = Logger(classOf[RestTemplateConfig])


  @Value("${project.httpClient.maxTotal}")
  @BeanProperty
  var maxTotal: Integer = 20

  @Value("${project.httpClient.defaultMaxPerRoute}")
  @BeanProperty
  var defaultMaxPerRoute: Integer = 2

  @Value("${project.httpClient.validateAfterInactivity}")
  @BeanProperty
  var validateAfterInactivity: Integer = 2000


  @Value("${project.httpClient.connectTimeout}")
  @BeanProperty
  var connectTimeout: Integer = 1000

  @Value("${project.httpClient.connectionRequestTimeout}")
  @BeanProperty
  var connectionRequestTimeout: Integer = 500

  @Value("${project.httpClient.socketTimeout}")
  @BeanProperty
  var socketTimeout: Integer = 10000

  /**
    * 1: 第一步，实例化一个连接池管理器，并设置最大连接数，最大并发数。
    *
    * @return PoolingHttpClientConnectionManager
    */
  @Bean(name = Array("httpClientConnectionManager"))
  def poolingHttpClientConnectionManager: PoolingHttpClientConnectionManager = {

    logger.info(s"maxTotal : $maxTotal," +
      s"defaultMaxPerRoute: $defaultMaxPerRoute, " +
      s"validateAfterInactivity: $validateAfterInactivity")

    CodingHelper.csar(new PoolingHttpClientConnectionManager()) {
      manager => {
        /* 最大连接数 */
        manager.setMaxTotal(maxTotal)
        /* 每路由最大连接数 */
        manager.setDefaultMaxPerRoute(defaultMaxPerRoute)
        manager.setValidateAfterInactivity(validateAfterInactivity)
      }
    }

  }


  /**
    * 2: RequestConfig
    *
    * @return
    */
  @Bean(name = Array("requestConfig"))
  def requestConfig: RequestConfig = {

    logger.info(s"connectTimemout: $connectTimeout; " +
      s"connectionRequestTimeout: $connectionRequestTimeout; " +
      s"socketTimeout: $socketTimeout")

    val builder = RequestConfig.custom

    // 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
    builder.setConnectTimeout(connectTimeout)
    // 从连接池中获取连接的超时时间，超过该时间未拿到可用连接，
    // 会抛出org.apache.http.conn.ConnectionPoolTimeoutException:
    // Timeout waiting for connection from pool
    builder.setConnectionRequestTimeout(connectionRequestTimeout)
    //服务器返回数据(response)的时间，超过该时间抛出read timeout
    builder.setSocketTimeout(socketTimeout)

    builder.build
  }


  /**
    * 3: 实例化连接池，设置连接池管理器。
    *
    * @param manager PoolingHttpClientConnectionManager
    * @return
    */
  @Bean
  def closeableHttpClient(
                           @Qualifier("httpClientConnectionManager")
                           manager: PoolingHttpClientConnectionManager,
                           @Qualifier("requestConfig")
                           requestConfig: RequestConfig): CloseableHttpClient = {

    logger.info("init >>>> CloseableHttpClient >>>>")
    val builder = HttpClientBuilder.create
    builder.setConnectionManager(manager)
    builder.setDefaultRequestConfig(requestConfig)
    builder.evictExpiredConnections
    builder.build

  }


  @Bean
  def httpRequestFactory(httpClient: CloseableHttpClient): ClientHttpRequestFactory = {
    logger.info("init >>>> ClientHttpRequestFactory >>>>")
    new HttpComponentsClientHttpRequestFactory(httpClient)
  }


  @Bean
  def restTemplate(httpRequestFactory: ClientHttpRequestFactory): RestTemplate = {
    logger.info("init >>>> RestTemplate >>>>")
    new RestTemplate(httpRequestFactory)
  }

}
