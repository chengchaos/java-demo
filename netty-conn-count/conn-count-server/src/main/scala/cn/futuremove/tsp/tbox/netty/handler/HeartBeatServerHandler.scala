package cn.futuremove.tsp.tbox.netty.handler

import com.typesafe.scalalogging.slf4j.LazyLogging
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}
import io.netty.handler.timeout.{IdleState, IdleStateEvent}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 实现其 {@code userEventTriggered() }方法，在出现超时事件时会被触发，包括读空闲超时或者写空闲超时；
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/29 0029 上午 11:16 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class HeartBeatServerHandler(limit: Int) extends ChannelInboundHandlerAdapter
  with LazyLogging {

  private var lossConnectCount = 0

  override def userEventTriggered(ctx: ChannelHandlerContext, evt: Any): Unit = {
    evt match {
      case event: IdleStateEvent =>
        if (event.state() == IdleState.READER_IDLE) {
          lossConnectCount += 1
          if (lossConnectCount > limit) {
            //logger.error("关闭这个不活跃通道！")
            //ctx.channel().close()
          }
        }
      case _ =>
        super.userEventTriggered(ctx, evt)
    }
  }

  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    lossConnectCount = 0
    logger.info("client says ==> {}", msg.toString)
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    logger.error("", cause)
    ctx.close()

  }
}
