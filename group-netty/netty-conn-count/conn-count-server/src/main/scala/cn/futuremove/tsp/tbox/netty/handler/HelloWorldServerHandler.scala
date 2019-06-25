package cn.futuremove.tsp.tbox.netty.handler

import com.typesafe.scalalogging.slf4j.LazyLogging
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}
import io.netty.util.internal.StringUtil

class HelloWorldServerHandler extends ChannelInboundHandlerAdapter with LazyLogging {


  @throws[Exception]
  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {

    logger.info("server channelRead ==> {}", msg.asInstanceOf[AnyRef])
    super.channelRead(ctx, msg)
  }

  @throws[Exception]
  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    logger.error(StringUtil.EMPTY_STRING, cause)
    ctx.close
  }
}
