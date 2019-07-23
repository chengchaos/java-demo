package com.example.myscala.sb.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题
  * </p>
  *
  * @author chengchao - 2019-07-19 17:21 <br />
  * @since [产品模块版本]
  * @see [相关类方法]
  *
  */
@Configuration
class TaskExecutorConfig {

  @Bean(Array("taskExecutor"))
  def taskExecutor: ThreadPoolTaskExecutor = {
    val cpu = Runtime.getRuntime.availableProcessors
    val taskExecutor = new ThreadPoolTaskExecutor
    taskExecutor.setCorePoolSize(cpu * 4)
    taskExecutor.setMaxPoolSize(2000)
    taskExecutor.setQueueCapacity(32)
    taskExecutor.setThreadNamePrefix("async-task")
    taskExecutor.getActiveCount
    taskExecutor.getPoolSize
    taskExecutor
  }
}
