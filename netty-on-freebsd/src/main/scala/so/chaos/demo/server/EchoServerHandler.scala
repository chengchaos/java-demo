package so.chaos.demo.server

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.{ChannelFutureListener, ChannelHandler, ChannelHandlerContext, ChannelInboundHandlerAdapter}
import io.netty.util.CharsetUtil
import io.netty.util.internal.StringUtil
import org.slf4j.LoggerFactory

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/14 0014 上午 11:34 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@ChannelHandler.Sharable
class EchoServerHandler extends ChannelInboundHandlerAdapter{

  private val logger = LoggerFactory.getLogger(classOf[EchoServerHandler])

  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    val in = msg.asInstanceOf[ByteBuf]

    if (logger.isInfoEnabled) {
      logger.info("Server received ==> {}", in.toString(CharsetUtil.UTF_8))
    }

    ctx.write(in)
  }

  override def channelReadComplete(ctx: _root_.io.netty.channel.ChannelHandlerContext): Unit = {
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
      .addListener(ChannelFutureListener.CLOSE)
  }

  override def exceptionCaught(ctx: _root_.io.netty.channel.ChannelHandlerContext, cause: _root_.java.lang.Throwable): Unit = {
    logger.error(StringUtil.EMPTY_STRING, cause)
    ctx.close
  }
}

object EchoServerHandler {

  def apply(): EchoServerHandler = new EchoServerHandler()
  val INSTANCE = EchoServerHandler()

}
