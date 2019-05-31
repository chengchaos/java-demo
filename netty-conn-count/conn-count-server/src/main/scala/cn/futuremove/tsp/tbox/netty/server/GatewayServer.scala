package cn.futuremove.tsp.tbox.netty.server

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import cn.futuremove.tsp.tbox.netty.server.business.Decoders
import com.typesafe.scalalogging.slf4j.Logger
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.{NioServerSocketChannel, NioSocketChannel}
import io.netty.channel.unix.UnixChannelOption
import io.netty.util.concurrent.{Future, GenericFutureListener}
import org.slf4j.LoggerFactory

class GatewayServer {

  private val logger: Logger = Logger(LoggerFactory.getLogger(classOf[GatewayServer]))


  private val bossGroupThreads: Int = Runtime.getRuntime.availableProcessors()

  def start(): (EventLoopGroup, EventLoopGroup) = {

    val bufferSize = 1024
    val cpu = Runtime.getRuntime.availableProcessors()

    logger.info("Gateway Server Starting ...")
    val parentGroup: EventLoopGroup = new NioEventLoopGroup(bossGroupThreads)
    val childGroup: EventLoopGroup = new NioEventLoopGroup()

    try {
      val serverBootStrap: ServerBootstrap = new ServerBootstrap()
      serverBootStrap.group(parentGroup, childGroup)
        .option(UnixChannelOption.SO_REUSEPORT, Boolean.box(true))
        .option(ChannelOption.SO_REUSEADDR, Boolean.box(true))
        .option(ChannelOption.SO_RCVBUF, Int.box(1024 * 1024 * bufferSize))
        .channel(classOf[NioServerSocketChannel])

        .childHandler(new ChannelInitializer[NioSocketChannel] {
          /**
            * 每次有新连接到来的时候，都会调用 ChannelInitializer 的 initChannel() 方法
            *
            * @param channel Channel
            */
          override def initChannel(channel: NioSocketChannel): Unit =
            channel.pipeline()
//              .addLast(Decoders.lengthFieldBasedFrameDecoder)
              .addLast(Decoders.authHandler)

              //.addLast(new HelloWorldServerHandler())
        })
        .childOption(ChannelOption.SO_KEEPALIVE, Boolean.box(true))
        .childOption(ChannelOption.TCP_NODELAY, Boolean.box(true))
        .option(ChannelOption.SO_BACKLOG, Int.box(1024))
        .localAddress(new InetSocketAddress(8384))

      for (i <- 1 to cpu) {

        val channelFuture: ChannelFuture = serverBootStrap.bind(8384)
          //        .sync()
          .addListener(new GenericFutureListener[Future[Void]]() {
          override def operationComplete(future: Future[Void]): Unit = {
            if (future.isSuccess) {
              logger.info("端口绑定成功 ==> {}", Int.box(i))
            } else {
              logger.info("端口绑定失败 ==> {}", Int.box(i))
            }
          }
        })
        channelFuture.channel().closeFuture().addListener(new ChannelFutureListener {
          override def operationComplete(future: ChannelFuture): Unit = {
            logger.info("{} 链路关闭", future.channel)
          }
        })
      }

      //channelFuture.channel().closeFuture().sync()
      (parentGroup, childGroup)

    } finally {
      //childGroup.shutdownGracefully().sync()
      //parentGroup.shutdownGracefully().sync()
    }
  }
}

object GatewayServer {
  def main(args: Array[String]): Unit = {
    val tuple2: (EventLoopGroup, EventLoopGroup) = new GatewayServer().start()

    println("等 20 秒")
    TimeUnit.SECONDS.sleep(20L)
    println("开始关闭。")
    println("关闭 parentGroup。")
    tuple2._1.shutdownGracefully().sync()
    println("关闭 childGroup。")
    tuple2._2.shutdownGracefully().sync()
    println("完成关闭。")
    System.exit(0)
  }
}
