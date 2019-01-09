package cn.chengchaos.cs

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorLogging}
import cn.chengchaos.la._

class RemoteActor extends Actor with ActorLogging{

  // 模拟处理结果状态，发送给消息的发送方：
  val SUCCESS = "success";
  val FAILURE = "failure";


  override def receive: Receive = {

    case Start => log.info("RECV enent: {}", Start)
    case Stop => log.info("RECV event: {}", Stop)

    case Shutdown(waitSecs) => {
      log.info("Wait to shutdown: waitSecs = {}", waitSecs)
      TimeUnit.SECONDS.sleep(waitSecs)
      log.info("Shutdown this system.")
      context.system.terminate()
    }

    case Heartbeat(id, magic) => log.info("RECV heartbeat: {}, {}", id, magic)
    case Header(id, len, encrypted) => log.info("RECV header: {}", (id, len, encrypted))
    case Packet(id, seq, content) => {
      val originalSender = sender()
      log.info("RECV packet: {}", id, seq, content)
      originalSender !(seq, SUCCESS)
    }
    case _ =>
  }
}
