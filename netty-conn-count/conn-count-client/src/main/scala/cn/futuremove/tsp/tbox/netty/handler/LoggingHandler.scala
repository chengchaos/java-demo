package cn.futuremove.tsp.tbox.netty.handler

import com.typesafe.scalalogging.slf4j.LazyLogging
import io.netty.channel.{ChannelHandler, ChannelHandlerContext, ChannelInboundHandlerAdapter}
import io.netty.util.internal.StringUtil

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/9 0009 下午 7:32 <br />
  * @since 1.1.0
  */
@ChannelHandler.Sharable
class LoggingHandler extends ChannelInboundHandlerAdapter with LazyLogging {

  override def channelRegistered(ctx: ChannelHandlerContext): Unit = {
    logger.info("+++ 1 channelRegistered +++")
    super.channelRegistered(ctx)
  }


  override def channelUnregistered(ctx: ChannelHandlerContext): Unit = {
    logger.info("+++ channelUnregistered +++")
    super.channelUnregistered(ctx)
  }


  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    logger.info("+++ 2 channelActive +++")
    super.channelActive(ctx)

  }

  override def channelInactive(ctx: ChannelHandlerContext): Unit = {
    logger.info("+++ channelInactive +++")
    super.channelInactive(ctx)
  }

  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    logger.info("+++ channelRead +++")
    super.channelRead(ctx, msg)
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    logger.info("+++ channelReadComplete +++")
    super.channelReadComplete(ctx)
  }

  override def userEventTriggered(ctx: ChannelHandlerContext, evt: Any): Unit = {
    logger.info("+++ userEventTriggered +++")
    super.userEventTriggered(ctx, evt)
  }

  override def channelWritabilityChanged(ctx: ChannelHandlerContext): Unit = {
    logger.info("+++ channelWritabilityChanged +++")
    super.channelWritabilityChanged(ctx)
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    logger.error(StringUtil.EMPTY_STRING, cause)
    ctx.close()
  }
}
