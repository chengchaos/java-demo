package so.chaos.demo.server

import java.net.InetSocketAddress

import io.netty.bootstrap
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelFuture, ChannelInitializer, ChannelOption, EventLoopGroup}
import io.netty.channel.kqueue.{KQueueChannelOption, KQueueEventLoopGroup, KQueueServerSocketChannel}
import io.netty.channel.socket.nio.NioChannelOption
import io.netty.channel.unix.UnixChannelOption
import io.netty.util.concurrent.{Future, GenericFutureListener}
import org.slf4j.LoggerFactory

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/14 0014 上午 11:11 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class KQueueEchoServer extends EchoServer {


  private val logger = LoggerFactory.getLogger(classOf[KQueueEchoServer])


  def start(): List[EventLoopGroup] = {
    val bufferSize = 1024
    val port = 7777

    val parentGroup: EventLoopGroup = new KQueueEventLoopGroup()
    val childGroup: EventLoopGroup = new KQueueEventLoopGroup()

    val server: ServerBootstrap = new bootstrap.ServerBootstrap()
    server.group(parentGroup, childGroup)
      .option(UnixChannelOption.SO_REUSEPORT, Boolean.box(true))
      .option(ChannelOption.SO_REUSEADDR, Boolean.box(true))
      .option(ChannelOption.SO_RCVBUF, Int.box(1024 * 1024 * bufferSize))
      .channel(classOf[KQueueServerSocketChannel])
      .handler(new ChannelInitializer[KQueueServerSocketChannel] {
        override def initChannel(ch: KQueueServerSocketChannel): Unit = {
          ch.pipeline()
            .addLast(EchoServerHandler.INSTANCE)
        }
      })
      //.childHandler()
      .localAddress(new InetSocketAddress(port))

    for (i <- 1 to 2) {
      server.bind()
        //.sync()
        .addListener(new GenericFutureListener[Future[_ >: Void]] {
        override def operationComplete(future: Future[_ >: Void]): Unit = {
          if (future.isSuccess) logger.info("bind success ==> {}", i)
          else logger.error("bootstrap bind fail ==> {}", i)
        }
      })

    }

    List(parentGroup, childGroup)

  }
}
