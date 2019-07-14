package com.example.demo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.client.RestTemplate

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/7/8 12:56 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */

@Configuration
class RestTemplateConfig(@Autowired restTemplateBuilder: RestTemplateBuilder) {



  @Bean
  def restTemplate(): RestTemplate = {
    this.restTemplateBuilder.build()
  }

}
