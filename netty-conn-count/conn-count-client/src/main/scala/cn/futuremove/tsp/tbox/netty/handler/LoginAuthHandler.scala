package cn.futuremove.tsp.tbox.netty.handler

import cn.futuremove.tsp.tbox.netty.server.business.Business
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/13 0013 下午 3:24 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@Sharable
class LoginAuthHandler extends LoggingHandler {

  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    if (Business.nonLogin(ctx.channel())) {
      logger.info("没有登录, 关闭 Channel")
      ctx.channel().close()
    } else {
      ctx.pipeline().remove(this)
      super.channelRead(ctx, msg)
    }
  }

  override def handlerRemoved(ctx: ChannelHandlerContext): Unit = {

    if (Business.hasLogin(ctx.channel())) {
     logger.info("当前连接登录验证完毕，无需再次验证, {} 被移除", this.getClass)
    } else {
      logger.info("无登录验证，{} 强制关闭连接!", this.getClass)
    }
  }
}
