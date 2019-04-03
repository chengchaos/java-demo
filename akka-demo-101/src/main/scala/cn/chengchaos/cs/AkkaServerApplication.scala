package cn.chengchaos.cs

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object AkkaServerApplication extends App {


  val system = ActorSystem("remote-system",
    ConfigFactory.load().getConfig("MyRemoteServerSideActor"))

  val log = system.log
  log.info("Remote server actor started: {}", system)

  system.actorOf(Props[RemoteActor], "remoteActor")

}
