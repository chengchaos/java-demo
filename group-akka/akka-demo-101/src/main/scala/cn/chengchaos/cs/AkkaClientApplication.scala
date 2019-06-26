package cn.chengchaos.cs

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.atomic.AtomicLong

import akka.actor.{ActorSystem, Props}
import cn.chengchaos.la._
import com.alibaba.fastjson.JSONObject
import com.typesafe.config.ConfigFactory

import scala.util.Random

object AkkaClientApplication extends App {

  val system = ActorSystem("client-system",
    ConfigFactory.load().getConfig("MyRemoteClientSideActor"))

  val log = system.log
  val clientActor = system.actorOf(Props[ClientActor], "clientActor")

  @volatile var running = true

  val hbInterval = 1000

  lazy val hbWorker = createHBWorker

  def createHBWorker: Thread = {
    new Thread("HB-WORKER") {
      override def run(): Unit = {
        while (running) {
          clientActor ! Heartbeat("HB",39264 )
          Thread.sleep(hbInterval)
        }
      }
    }
  }

  def format(timestamp: Long, format: String) :String = {
    val sdf = new SimpleDateFormat(format)
    sdf.format(new Date(timestamp))
  }

  def createPacket(packet: Map[String, _]):JSONObject = {
    val pkt = new JSONObject()
    packet.foreach(p => pkt.put(p._1, p._2))
    pkt
  }

  val ID = new AtomicLong(90760000)

  def nextTxID: Long = {
    ID.incrementAndGet()
  }

  clientActor ! Start
  Thread.sleep(2000)

  clientActor ! Header("HEADER", 20, encrypted = false)
  Thread.sleep(2000)

  val DT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
  val r = Random
  val packetCount = 100

  val serviceProviders = Seq("CMCC", "AKBBC", "OLE")

  val payServiceProvicers = Seq("PayPal", "CMB", "ICBC", "ZMB", "XXB")
  def nextProvider(seq: Seq[String]) :String = {
    seq(r.nextInt(seq.size))
  }

  val startWhen = System.currentTimeMillis()

  for (i <- 0 until packetCount) {
    val pkt = createPacket(Map[String, Any](
      "txid" -> nextTxID,
      "pvid" -> nextProvider(serviceProviders),
      "txtm" -> format(System.currentTimeMillis(), DT_FORMAT),
      "payp" -> nextProvider(payServiceProvicers),
      "amount" -> 1000 * r.nextFloat()
    ))
    clientActor ! Packet("PKT", System.currentTimeMillis(), pkt.toString)
  }

  val finishWhen = System.currentTimeMillis()

  log.info("Finish: timeTaken ==> {}, avg ==> {}"
    , finishWhen - startWhen
    , (packetCount + 0D) / (finishWhen - startWhen))

  Thread.sleep(2000)

  // ask remote actor to shutdown

  val waitSecs = hbInterval
  clientActor ! Shutdown(waitSecs)

  running = false

  while (hbWorker.isAlive) {
    log.info("Wait heartbeat worker to exit ...")
    Thread.sleep(300)
  }

  system.terminate()
}
