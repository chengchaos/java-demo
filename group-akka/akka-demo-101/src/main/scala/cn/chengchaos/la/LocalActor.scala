package cn.chengchaos.la

import akka.actor.{Actor, ActorLogging}

/**
  * 实现一个 Actor， 继承自 `akka.actor.Actor`
  *
  * 然后实现 Actor trial 的 `receive` 方法
  *
  * 可以混入另一个 Trail `akka.actor.ActorLogging`
  */
class LocalActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case Start => log.info("start ...")
    case Stop => log.info("stop ... !")
    case Heartbeat(id, magic) => log.info("heartbeat id ==> {}, magic ==> {}", id, magic)
    case Header(id, len, encrypted) => log.info("header: {}, {}, {}", id, len, encrypted)
    case Packet(id, seq, content) => log.info("packet: "+ (id, seq, content))
    case _ =>
  }

}
