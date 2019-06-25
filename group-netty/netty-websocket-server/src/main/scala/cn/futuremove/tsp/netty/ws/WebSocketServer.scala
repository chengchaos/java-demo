package cn.futuremove.tsp.netty.ws

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelFutureListener, ChannelInitializer, EventLoopGroup}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.codec.http.{HttpObjectAggregator, HttpServerCodec}
import io.netty.handler.stream.ChunkedWriteHandler

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/25 13:17 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object WebSocketServer {

  def main(args: Array[String]): Unit = {

    start(WebSocketConfig.INET_PORT)
  }

  def nsar[A](whatever: A) (callback: A => Any) : A = {
    callback(whatever)
    whatever
  }


  def start(inetPort: Int) : (EventLoopGroup, EventLoopGroup) = {

    nsar(Tuple2(new NioEventLoopGroup(1), new NioEventLoopGroup())) {
      tuple2 => new ServerBootstrap()
          .group(tuple2._1, tuple2._2)
          .channel(classOf[NioServerSocketChannel])
          .childHandler(new ChannelInitializer[SocketChannel] {
            override def initChannel(ch: SocketChannel): Unit = ch.pipeline()
              .addLast("http-codec", new HttpServerCodec)
              .addLast("aggregator", new HttpObjectAggregator(65536))
              .addLast("http-chunked", new ChunkedWriteHandler)
//              .addLast("compress", new WebSocketServerCompressionHandler())
              .addLast("websocket", new WebSocketHandler)


          })
          .bind(inetPort)
          .sync()
          .channel()
          .closeFuture()
          .addListener(ChannelFutureListener.CLOSE_ON_FAILURE)
    }
  }
}
