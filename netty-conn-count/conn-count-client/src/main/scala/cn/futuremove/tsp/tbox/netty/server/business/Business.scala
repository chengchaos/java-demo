package cn.futuremove.tsp.tbox.netty.server.business

import cn.futuremove.tsp.tbox.global.ChannelHolder
import io.netty.channel.Channel
import io.netty.util.Attribute
import org.slf4j.{Logger, LoggerFactory}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/13 0013 下午 3:20 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object Business {

  val logger: Logger = LoggerFactory.getLogger(Business.getClass)


  def hasLogin(channel: Channel): Boolean = {
    val loginAttr: Attribute[Boolean] = channel.attr(Attributes.LOGIN)
    if (loginAttr != null) {
      loginAttr.get
    } else {
      false
    }
  }


  def nonLogin(channel: Channel): Boolean = !hasLogin(channel)


  def markAsLogin(channel: Channel, sn: Int, vin: String): Unit = {
    logger.info("标记为登录成功")
    channel.attr(Attributes.LOGIN).set(Boolean.box(true))
    channel.attr(Attributes.SN).set(Int.box(sn))
    channel.attr(Attributes.VIN).set(vin)

    ChannelHolder.addChannel(vin, channel)

  }
}
