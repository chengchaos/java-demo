package com.example.demo.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/7/10 9:55 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@Configuration
class AsyncTaskExecutorConfig {


  @Bean(name=Array("threadPoolTaskExecutor"))
  def threadPoolAsyncTaskExecutor() : ThreadPoolTaskExecutor = {

    val cpu = Runtime.getRuntime.availableProcessors()

    val executor = new ThreadPoolTaskExecutor
    executor.setCorePoolSize(cpu * 4)
    executor.setMaxPoolSize(2000)
    executor.setQueueCapacity(32)
    executor.setThreadNamePrefix("a-web-task")
    executor.initialize()

    executor
  }

}
