package cn.futuremove.tsp.tbox.global

import java.util.concurrent.ConcurrentHashMap

import io.netty.channel.Channel


/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/13 0013 下午 8:44 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object ChannelHolder {

  private val holder: ConcurrentHashMap[String, Channel] =
    new ConcurrentHashMap()

  def addChannel(vin: String, channel: Channel): Unit = holder.put(vin, channel)

  def getChannel(vin: String): Option[Channel] = Option(holder.get(vin))

  def removeChannel(vin: String): Option[Channel] = Option(holder.remove(vin))


  import scala.collection.JavaConversions._

  import scala.collection.JavaConverters._

  def underlyingKeys: Set[String] = {
    val theSet = holder.keySet.asScala.toSet
    theSet
  }

  def underlyingValues: Vector[Channel]  = {
    val theVector = holder.values().asScala.toVector
    theVector
  }

}
