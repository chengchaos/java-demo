package cn.futuremove.tsp.tbox.netty.server.business

import io.netty.util.AttributeKey

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/13 0013 下午 3:26 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object Attributes {

  val LOGIN: AttributeKey[Boolean] = AttributeKey.newInstance("login")

  val SN: AttributeKey[Int]  = AttributeKey.newInstance("sn")

  val VIN: AttributeKey[String] = AttributeKey.newInstance("vin")

}
