package cn.futuremove.tsp.netty.ws

import java.util.concurrent.ConcurrentHashMap

import io.netty.channel.ChannelId
import io.netty.channel.group.{ChannelGroup, DefaultChannelGroup}
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
import io.netty.util.concurrent.GlobalEventExecutor

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/25 13:11 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object WebSocketConfig {


  val INET_PORT : Int = 8888

  val WEBSOCKET_URL: String = "ws://localhost:8888/websocket"

  val CHANNEL_GROUP: ChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)

  val HANDSHAKER_HOLDER: ConcurrentHashMap[ChannelId, WebSocketServerHandshaker] =
    new ConcurrentHashMap[ChannelId, WebSocketServerHandshaker]()

}
