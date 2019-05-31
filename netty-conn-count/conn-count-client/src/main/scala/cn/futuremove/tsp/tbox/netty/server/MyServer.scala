package cn.futuremove.tsp.tbox.netty.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelInitializer, nio}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.{NioServerSocketChannel, NioSocketChannel}
import io.netty.handler.timeout.IdleStateHandler
import java.util.concurrent.TimeUnit

import cn.futuremove.tsp.tbox.netty.handler.HeartBeatServerHandler
import io.netty.handler.codec.string.StringDecoder

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
class MyServer {

  def start() : Unit = {

    val parentGroup = new NioEventLoopGroup(1)
    val childGroup = new nio.NioEventLoopGroup()

    val serverBootstrap = new ServerBootstrap()
    serverBootstrap.group(parentGroup, childGroup)
    serverBootstrap.channel(classOf[NioServerSocketChannel])
    serverBootstrap.childHandler(new ChannelInitializer[NioSocketChannel] {
      override def initChannel(ch: NioSocketChannel): Unit = {

        val pipeline = ch.pipeline()
        // int readerIdleTimeSeconds
        // int writerIdleTimeSeconds
        // int allIdleTimeSeconds
        pipeline.addLast(new IdleStateHandler(
          5,
          0,
          0,
          TimeUnit.SECONDS))
        pipeline.addLast(new StringDecoder())
        pipeline.addLast(new HeartBeatServerHandler(10))

      }
    })



  }

}
