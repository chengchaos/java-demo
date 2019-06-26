package cn.chengchaos.la

import akka.actor.{ActorSystem, Props}
import com.alibaba.fastjson.JSONObject

//import scala.language.postfixOps

object LocalClient extends App {


  /* Local Actor */
  /* 创建一个ActorSystem对象，用来管理Actor实例 */
  val system = ActorSystem("local-system")

  println(system)

  // 通过ActorSystem对象，获取到一个Actor的引用
  val localActorRef = system.actorOf(Props(classOf[LocalActor]), name="local-server")

  println(localActorRef)

  localActorRef ! Start
  localActorRef ! Heartbeat("3099100", 0xabcd)

  val content = new JSONObject()
  content.put("name", "Stone")
  content.put("empid", 51082001)
  content.put("score", 89.3681)

  localActorRef ! Packet("3000001",
    System.currentTimeMillis(), content.toString)

  localActorRef ! Stop
  system.terminate

}
