package cn.futuremove.tsp

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/20 10:59 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
package object tbox {

  implicit def block2Runnable(block: => Unit): Runnable = {
    new Runnable {
      override def run(): Unit = block
    }
  }
}
