package cn.futuremove.tsp.tbox.netty.server

import java.util
import java.util.UUID

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelFuture, ChannelFutureListener, ChannelHandlerContext, ChannelInitializer, SimpleChannelInboundHandler, nio}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.{NioServerSocketChannel, NioSocketChannel}
import io.netty.handler.timeout.IdleStateHandler
import java.util.concurrent.TimeUnit

import cn.futuremove.tsp.tbox.global.ChannelHolder
import cn.futuremove.tsp.tbox.netty.handler.HeartBeatServerHandler
import cn.futuremove.tsp.tbox.netty.server.business.Attributes
import com.typesafe.scalalogging.slf4j.LazyLogging
import io.netty.buffer.Unpooled
import io.netty.handler.codec.MessageToMessageDecoder
import io.netty.handler.codec.string.StringDecoder
import io.netty.util.{Attribute, CharsetUtil}
import javax.swing.Box

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/29 0029 上午 11:05 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class MyServer extends LazyLogging {

  def start(inetPort : Int) : Unit = {

    val parentGroup = new NioEventLoopGroup(1)
    val childGroup = new nio.NioEventLoopGroup()

    val serverBootstrap = new ServerBootstrap()
    serverBootstrap.group(parentGroup, childGroup)
    serverBootstrap.channel(classOf[NioServerSocketChannel])
    serverBootstrap.childHandler(new ChannelInitializer[NioSocketChannel] {
      override def initChannel(ch: NioSocketChannel): Unit = {

        val pipeline = ch.pipeline()
        /*
         * - int readerIdleTimeSeconds
         * 读空闲超时时间设定，如果 channelRead() 方法超过 readerIdleTime
         * 时间未被调用则会触发超时事件调用 userEventTrigger() 方法
         * - int writerIdleTimeSeconds
         * 写空闲超时时间设定，如果 write() 方法超过 writerIdleTim 时间未被调用
         * 则会触发超时事件调用 userEventTrigger() 方法
         * - int allIdleTimeSeconds
         * 所有类型的空闲超时时间设定，包括读空闲和写空闲
         */

        pipeline.addLast(new IdleStateHandler(
          5,
          0,
          0,
          TimeUnit.SECONDS))
        pipeline.addLast(new StringDecoder())
        pipeline.addLast(new SimpleChannelInboundHandler[String]() {

          override def channelActive(ctx: ChannelHandlerContext): Unit = {

            val channel = ctx.channel()
            val attr:Attribute[String] = channel.attr[String](Attributes.VIN)
            var vin = attr.get()

            if (vin == null) {
              vin = UUID.randomUUID().toString
              channel.attr(Attributes.VIN).set(vin)
              ChannelHolder.addChannel(vin, channel)
            }
          }

          override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
            val byteBuf = Unpooled.directBuffer()
            byteBuf.writeCharSequence(msg, CharsetUtil.UTF_8)
            ctx.channel()
              .writeAndFlush(byteBuf)
          }

          override def channelInactive(ctx: ChannelHandlerContext): Unit = {
            val channel = ctx.channel()
            val attr:Attribute[String] = channel.attr[String](Attributes.VIN)
            val vin = attr.get()

            if (vin != null) {
              ChannelHolder.removeChannel(vin)
            }
            ctx.close()
          }
        })

        pipeline.addLast(new HeartBeatServerHandler(10))

      }
    })

    serverBootstrap.bind(inetPort).sync()
      .addListener(new ChannelFutureListener() {
        override def operationComplete(future: ChannelFuture): Unit = {
          val success = future.isSuccess
          logger.info("bind  {} ... {}", Int.box(inetPort), success.asInstanceOf[AnyRef])

        }
      })
  }
}
