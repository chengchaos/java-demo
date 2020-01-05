package cn.chengchaos.gj.chaos.detect.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Configuration

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]E470 - 2019/10/31 16:16 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Configuration
class RestTemplateConfig {

  import org.springframework.context.annotation.Bean
  import org.springframework.http.client.ClientHttpRequestFactory
  import org.springframework.http.client.SimpleClientHttpRequestFactory
  import org.springframework.web.client.RestTemplate


  @LoadBalanced
  @Bean(name = Array("loadBalanced"))
  def restTemplate(factory: ClientHttpRequestFactory) = new RestTemplate(factory)

  @Bean def simpleClientHttpRequestFactory: ClientHttpRequestFactory = {
    val factory = new SimpleClientHttpRequestFactory
    /* 单位为ms */
    factory.setReadTimeout(5000)
    factory.setConnectTimeout(5000)
    factory
  }
}
