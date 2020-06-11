package cn.futuremove.tsp.tbox.config

import org.springframework.beans.factory.annotation.Value

import scala.annotation.meta.{beanGetter, beanSetter}

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/31 18:17 <br />
 * @since [产品模块版本]
 * @see [相关类方法]
 *
 */
class Ip(ipAddress: String) {


  def ip() : String = ipAddress

}
