package cn.futuremove.tsp.netty.ws

import java.time.LocalDateTime

import grizzled.slf4j.Logger
import io.netty.buffer.Unpooled
import io.netty.channel._
import io.netty.handler.codec.http.websocketx._
import io.netty.handler.codec.http._
import io.netty.util.CharsetUtil

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/25 13:25 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@ChannelHandler.Sharable
class WebSocketHandler extends SimpleChannelInboundHandler[Object] {

  private val logger: Logger = Logger(classOf[WebSocketHandler])

  override def channelActive(ctx: ChannelHandlerContext): Unit = {

    logger.info("与客户端连接建立")
    WebSocketConfig.CHANNEL_GROUP.add(ctx.channel())

  }

  override def channelInactive(ctx: ChannelHandlerContext): Unit = {
    logger.info("与客户端连接断开")
    WebSocketConfig.CHANNEL_GROUP.remove(ctx.channel())
    WebSocketConfig.HANDSHAKER_HOLDER.remove(ctx.channel().id())
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    logger.error("Throwable :", cause)
  }

  override def channelRead0(ctx: ChannelHandlerContext, msg: Object): Unit = {

    val setSize = WebSocketConfig.CHANNEL_GROUP.size()
    val mapSize = WebSocketConfig.HANDSHAKER_HOLDER.size()
    logger.info(s"set size ==> $setSize, map size ==> $mapSize")
    msg match {
      case req: FullHttpRequest => this.handleHttpRequest(ctx, req)

      case frame: WebSocketFrame => this.handleWebSocket(ctx, frame)

      case _ =>

    }
  }

  private def handleHttpRequest(ctx: ChannelHandlerContext, req: FullHttpRequest) : Unit = {

    if (!req.decoderResult().isSuccess ||  !"websocket".equals(req.headers().get("Upgrade"))) {
      this.sendHttpResponse(ctx, req,
        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST))
      return
    }

    val wsFactory: WebSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(
      WebSocketConfig.WEBSOCKET_URL, null, false)
    val handshaker: WebSocketServerHandshaker = wsFactory.newHandshaker(req)

    val channel = ctx.channel()
    if (handshaker == null) {
      WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel)
    } else {
      WebSocketConfig.HANDSHAKER_HOLDER.put(channel.id(), handshaker)
      handshaker.handshake(channel, req)
    }
  }

  private def handleWebSocket(ctx: ChannelHandlerContext, frame: WebSocketFrame) : Unit = {

    val channel = ctx.channel()
    val channelId = channel.id()
    frame match {
      case close: CloseWebSocketFrame =>

        val handshaker = WebSocketConfig.HANDSHAKER_HOLDER.get(channelId)
        if (handshaker != null) {
          handshaker.close(channel, close.retain())
        }

      case ping: PingWebSocketFrame =>
        ctx.writeAndFlush(new PongWebSocketFrame(ping.content().retain()))

      case text: TextWebSocketFrame =>
        val content = text.text()
        logger.info(s"收到客户端发送请求 ==> $content")

        val data = new TextWebSocketFrame(
          LocalDateTime.now() +
            ", "+ channelId +
            ", "+ content
        )

        WebSocketConfig.CHANNEL_GROUP.writeAndFlush(data)

      case _ =>
        throw new UnsupportedOperationException("不支持二进制消息")
    }
  }

  private def sendHttpResponse(ctx: ChannelHandlerContext,
                               req: FullHttpRequest,
                               res: FullHttpResponse) : Unit = {

    if (res.status().code() != 200) {
      val byteBuffer = CharsetUtil.UTF_8.encode(res.status().toString)
      val buf = Unpooled.copiedBuffer(byteBuffer.array())
      res.content().writeBytes(buf)
      buf.release()
      val cf: ChannelFuture = ctx.channel().writeAndFlush(res)
      cf.addListener(ChannelFutureListener.CLOSE)
    }
  }
}
