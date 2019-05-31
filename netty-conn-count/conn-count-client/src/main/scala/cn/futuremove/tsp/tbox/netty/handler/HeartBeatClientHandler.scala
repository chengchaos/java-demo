package cn.futuremove.tsp.tbox.netty.handler

import java.util.Date

import com.typesafe.scalalogging.slf4j.LazyLogging
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}
import io.netty.handler.timeout.{IdleState, IdleStateEvent}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/29 0029 上午 11:59 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class HeartBeatClientHandler(beatTime: Int) extends ChannelInboundHandlerAdapter
  with LazyLogging {



  private var curTime = 0


  override def userEventTriggered(ctx: ChannelHandlerContext, evt: Any): Unit = {

    super.userEventTriggered(ctx, evt)
    evt match {
      case event: IdleStateEvent =>
        if (event.state() == IdleState.WRITER_IDLE) {
          if (curTime < beatTime) {
            curTime += 1
            //ctx.writeAndFlush("biubiu")
          }
        }
      case _ =>

    }
  }
}
