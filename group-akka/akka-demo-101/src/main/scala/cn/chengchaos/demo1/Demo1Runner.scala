package cn.chengchaos.demo1

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import cn.chengchaos.demo1.actors.BossActor

/**
  * 参考：
  *
  * https://www.jianshu.com/p/f8252ae64063
  *
  *
  */
object Demo1Runner {

  // 首先创建一家公司
  val actorSystem = ActorSystem("company-system")

  val chaosDispatcher = "chaos-dispatcher";

  def main(args:Array[String]) :Unit = {

    // 创建 Actor ：第一种方式：　利用　ActorSystem.actorOf
    val  bossActor = actorSystem.actorOf(Props[BossActor].withDispatcher(Demo1Runner.chaosDispatcher), "boss")


    // //从市场上观察到健身行业将会有很大的前景
    bossActor ! Business("Fitness industry has great prospects")

    TimeUnit.SECONDS.sleep(10L)
    actorSystem.terminate()

  }
}
