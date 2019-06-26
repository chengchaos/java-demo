package cn.chengchaos.cs

import akka.actor.{Actor, ActorLogging}
import cn.chengchaos.la.{Header, Heartbeat, Packet, Shutdown, Start, Stop}

class ClientActor extends Actor with ActorLogging {

  // akka.<protocol>://<actor system>@<hostname>:<port>/<actor path>

  val path = "akka.tcp://remote-system@127.0.0.1:2552/user/remoteActor"
  private val remoteServerRef = context.actorSelection(path)

  @volatile var connected = false
  @volatile var stop = false


  override def receive: Receive = {


    case Start => {
      // 发送Start消息表示要与远程Actor进行后续业务逻辑处理的通信，
      // 可以指示远程Actor初始化一些满足业务处理的操作或数据
      send(Start)
      if (!connected) {
        connected = true
        log.info("Actor connected: {}", this)
      }
    }

    case Stop => {
      send(Stop)
      stop = true
      connected = false
    }

    case header:Header => send(header)
    case hb:Heartbeat => sendWithCheck(hb)
    case pkt:Packet => sendWithCheck(pkt)
    case cmd: Shutdown => send(cmd)

    case (seq, result) => log.info("RESULT: seq => {}, result +> {}", seq, result)

    case m => log.info("Unknown message => {}", m)
  }

  private def send(cmd: Serializable):Unit = {

    log.info("Send command to server: {}", cmd)
    try {
      // 发送一个消息到远程Actor，消息必须是可序列化的，因为消息对象要经过网络传输
      remoteServerRef ! cmd
    } catch {
      case e:Exception => {
        connected = false
        log.info("Try to connect by sending Start command ...")
        send(Start)
      }
    }
  }

  private def sendWithCheck(cmd:Serializable) :Unit = {

    while (!connected) {
      Thread.sleep(100)
      log.info("Wait to be connected ...")
    }
    if (!stop) {
      send(cmd)
    } else {
      log.warning("Actor has stopped!")
    }
  }
}
