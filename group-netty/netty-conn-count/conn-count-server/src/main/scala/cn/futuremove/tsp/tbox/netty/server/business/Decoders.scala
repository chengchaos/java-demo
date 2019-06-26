package cn.futuremove.tsp.tbox.netty.server.business

import cn.futuremove.tsp.tbox.netty.handler._
import io.netty.handler.codec.LengthFieldBasedFrameDecoder

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/9 0009 下午 6:56 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object Decoders {

  private val maxFrameLength = Integer.MAX_VALUE
  private val lengthFieldOffset = 22
  private val lengthFieldLength = 2
  private val lengthAdjustment = 1
  private val initialBytesToStrip = 0

  def lengthFieldBasedFrameDecoder : LengthFieldBasedFrameDecoder =
    new LengthFieldBasedFrameDecoder(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip)



  val authHandler: LoginAuthHandler = new LoginAuthHandler()
}
