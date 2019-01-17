package cn.chengchaos.demo1

import akka.actor.ActorPath

/**
  * 一些消息类
  */
trait Message {

  val content: String
}

case class Business(content: String) extends Message {}
case class Meeting(content: String) extends Message {}
case class Confirm(content: String, actorPath: ActorPath) extends Message {}
case class DoAction(content: String) extends Message {}
case class Done(content: String) extends Message {}


