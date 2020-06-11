package cn.futuremove.tsp.tbox.netty

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import cn.futuremove.tsp.tbox.global.ChannelHolder
import cn.futuremove.tsp.tbox.netty.handler.HeartBeatClientHandler
import com.typesafe.scalalogging.slf4j.LazyLogging
import io.netty.bootstrap.Bootstrap
import io.netty.channel.{ChannelFuture, ChannelFutureListener, ChannelHandlerContext, ChannelInboundHandlerAdapter, ChannelInitializer, SimpleChannelInboundHandler}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.{StringDecoder, StringEncoder}
import io.netty.handler.timeout.IdleStateHandler

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/29 0029 上午 11:44 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class MyClient(ip: String) extends LazyLogging {


  def connect(inetPort: Int) : NioEventLoopGroup = {

    val eventGroup = new NioEventLoopGroup()

    val bootstrap = new Bootstrap
    bootstrap.group(eventGroup)
      .channel(classOf[NioSocketChannel])
      .handler(new ChannelInitializer[SocketChannel] () {
        override def initChannel(ch: SocketChannel): Unit = {
          val pipeline = ch.pipeline()
          pipeline.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS))
          pipeline.addLast(new StringDecoder())
          pipeline.addLast(new StringEncoder())
          pipeline.addLast(new HeartBeatClientHandler(10))
          pipeline.addLast(new SimpleChannelInboundHandler[String]() {
            override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
              logger.info("receive ==> {}", msg)
            }
          })
        }
      })

    bootstrap.connect(new InetSocketAddress(ip, inetPort))
      .sync()
      .addListener(new ChannelFutureListener {
        override def operationComplete(future: ChannelFuture): Unit = {
          if (future.isSuccess) {
            logger.info("connected ...")
            ChannelHolder.addChannel("chengchao", future.channel())
          }
        }
      })
      .channel()
      .closeFuture()
      .addListener(new ChannelFutureListener {
        override def operationComplete(future: ChannelFuture): Unit = {
          logger.info("{} 链路关闭", future.channel.asInstanceOf[AnyRef])
          logger.info("isActive ==> {}", future.channel().isActive.asInstanceOf[AnyRef])
          logger.info("isWritable ==> {}", future.channel().isWritable.asInstanceOf[AnyRef])
          logger.info("isOpen ==> {}", future.channel().isOpen.asInstanceOf[AnyRef])
        }
      })

    if (logger.underlying.isInfoEnabled) {
      logger.info("连接完成,")
    }
    eventGroup
  }
}
