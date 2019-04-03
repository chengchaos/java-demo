package cn.chengchaos.demo1.actors

import akka.actor.Actor
import akka.event.Logging
import cn.chengchaos.demo1.{DoAction, Done}

class WorkerActor extends Actor {

  val log = Logging(context.system, this)

  override def receive: Receive = {
    case d: DoAction =>
      log.info("I have receive task...")
      sender() ! Done("I have done work")
  }
}
