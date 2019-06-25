package cn.chengchaos.demo1.actors

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, Props}
import akka.event.Logging
import cn.chengchaos.demo1.{Confirm, DoAction, Meeting}

class ManagerActor extends Actor {

  val log = Logging(context.system, this)


  override def receive: Receive = {

    case m: Meeting => {
      log.info("receive meeting .. ==> {}", m)
      sender() ! Confirm("I have receive command", self.path)
    }
    case d: DoAction => {
      TimeUnit.SECONDS.sleep(2L)
      val workerActor = context.actorOf(Props[WorkerActor], "worker")
      log.info("the worker is ... ==> {}", workerActor)
      workerActor forward d
    }
  }
}
