package so.chaos.demo.server

import io.netty.channel.EventLoopGroup
import io.netty.channel.kqueue.KQueue
import org.slf4j.LoggerFactory

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/14 0014 上午 11:10 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
abstract class EchoServer {
  def start(): List[EventLoopGroup]
}

object EchoServer {


  private val logger = LoggerFactory.getLogger(EchoServer.getClass)

  def getEchoServer: EchoServer = {

    logger.info("class ==> {}", EchoServer.getClass)
    logger.info("kquery available ==> {}", KQueue.isAvailable)

    if (KQueue.isAvailable) new KQueueEchoServer
    else new NioEchoServer
  }
}
