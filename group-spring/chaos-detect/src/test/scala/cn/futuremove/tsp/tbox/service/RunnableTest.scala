package cn.futuremove.tsp.tbox.service

import java.util.Optional
import java.util.concurrent.{Executors, TimeUnit}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/10 13:40 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class RunnableTest {


  var someval: String = _

  @org.junit.Test def testRun(): Unit = {

    val pool = Executors.newSingleThreadExecutor()

    import scala.compat.java8.FunctionConverters._
    val f1 = () => "hello world"
    val f2 = f1.asJava
    val javaOpt:Optional[String] = Optional.ofNullable(someval)

    val v = javaOpt.orElseGet(f2)


    println("v = "+ v)

    println("等它执行完成")
    TimeUnit.SECONDS.sleep(1L)
  }

}
