package cn.futuremove.tsp.conf

import java.util.concurrent.TimeUnit

import com.typesafe.config.{Config, ConfigFactory}
import grizzled.slf4j.Logger
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/25 15:02 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object AppConfig {

  val logger: Logger = Logger[AppConfig.type]

  /*
   * 默认值： reference.conf
   */
  val conf: Config = ConfigFactory.load("my")

  val onlyTestConnect: Boolean = conf.getBoolean("onlyTestConnect")
  val clients = new DefaultChannelGroup("activeWebsocketClients", GlobalEventExecutor.INSTANCE)
  val totalSize = conf.getInt("totalSize")

  val port = conf.getInt("server.port")
  val delay = conf.getDuration("sending.timer.delay", TimeUnit.MILLISECONDS)
  val interval = conf.getDuration("sending.timer.interval", TimeUnit.MILLISECONDS)

  def main(args: Array[String]): Unit = {
    logger.info(s"config ==> $conf")
    logger.info(s"port ==> $port")
    logger.info(s"totalSize ==> $totalSize")

    val username = conf.getString("client.username")
    val password = conf.getString("client.password")

    logger.info(s"user ==> $username, pwd ==> $password")

  }
}
