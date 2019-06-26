package cn.chengchaos.futuredemo

import java.util.concurrent.TimeUnit

import org.slf4j.LoggerFactory

import scala.util.{Success, Try}

/**
  * 参考：
  *
  * https://www.jianshu.com/p/0b1feed12149
  *
  */
object Demo1 {

  private val LOGGER = LoggerFactory.getLogger("Demo1")


  def main(args: Array[String]): Unit = {

    import scala.concurrent._
    import ExecutionContext.Implicits.global

    /* 创建一个 Future */
    val future: Future[String] = Future {
      TimeUnit.SECONDS.sleep(1L)
      "Hello world"
    }

//    val ots: Option[Try[String]] = future.value
//    val isCompleted: Boolean = future.isCompleted

    /* 使用回调 */
    future onComplete {
      case Success(r) => println(s"the result is $r")
      case _ => println("Some Exception ...")
    }

    /* 使用 flatMap 组合 */

    val fut1 = Future {
      println("enter task 1")
      Thread.sleep(2000)
      1 + 1
    }

    val fut2 = Future {
      println("enter task 2")
      Thread.sleep(1000)
      2 + 2
    }

    fut1.flatMap {
      v1 => fut2.map {
        v2 => println(s"thr result is ${v1 + v2}")
      }
    }

    fut1.flatMap(v3
      => fut2.map(v4 =>
        println(s"the ... ${v3 + v4}")))


    /*  使用  for yield */

    for {
      v1 <- fut1
      v2 <- fut2
    } yield println(s"use for yield ... ==> ${ v1 + v2}")


    /*
     * https://github.com/scala/scala-async/tree/2.11.x
     */
    /* 使用 scala-async */

    import scala.async.Async.{async, await}

    val v1 = async {
      await(fut1) + await(fut2)
    }

    v1.foreach(i => println(s"use async ... ${i}")
    )

    println("I am working")
    Thread.sleep(5000)

  }

}
