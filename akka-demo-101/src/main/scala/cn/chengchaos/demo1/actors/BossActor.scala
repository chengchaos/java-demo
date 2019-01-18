package cn.chengchaos.demo1.actors

import akka.actor.{Actor, Props}
import akka.event.Logging
import akka.util.Timeout
import cn.chengchaos.demo1._

import scala.concurrent.duration.{Duration, TimeUnit}
import akka.pattern.ask

class BossActor extends Actor {

  val log = Logging(context.system, this)

  implicit val asktimeout = Timeout(Duration(5, "second"))

  import context.dispatcher

  var taskCount = 0


  override def receive: Receive = {
    case b: Business =>
      log.info("I must to do some thing, go, go, go!")
      log.info("self.path.address ==> {}", self.path.address)
      // 创建 ActorRef 的另一种方法：
      // 利用 ActorContext.actorOf
      val managerActors = (1 to 3).map { i =>
        // 这里我们召唤3个主管
        context.actorOf(Props[ManagerActor], s"manager$i")
      }

      managerActors foreach (manager => {
        log.info("the manager is ... ==> {}", manager)
        manager ? Meeting("Metting to discuss big plans") map {
          case c: Confirm =>
            // 每个节点有且只有一个父节点
            log.info("actorPath.parent ==> {}", c.actorPath.parent.toString)
            // 根据 actor 路径查找已经存在的 Actor 获得 ActorRef
            // 这里 `c.actorPath` 是绝对路径，
            // 也可以根据相对路径得到
            log.info("actorPath ==> {}", c.actorPath)
            val manager = context.actorSelection(c.actorPath)
            manager ! DoAction("Do thing")

        }
      })

    case d: Done =>
      taskCount += 1
      if (taskCount == 3) {
        log.info("the project is done, we will earn much money!")
        context.system.terminate()
      }

  }
}
