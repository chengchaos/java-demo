package cn.futuremove.tsp.tbox

import java.util.concurrent.{Executors, TimeUnit}

import org.junit.Test

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 1:29 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class MyTest {

  implicit def block2Runnable(block: => Unit): Runnable = {
    new Runnable {
      override def run(): Unit = block
    }
  }

  @Test
  def runnableTest(): Unit = {
    val executor = Executors.newSingleThreadExecutor
    executor.submit({
      for (i <- 1 to 100) {
        println(i)
        TimeUnit.SECONDS.sleep(1)
      }
    })

    TimeUnit.SECONDS.sleep(10L)

    executor.shutdown()
  }
}


