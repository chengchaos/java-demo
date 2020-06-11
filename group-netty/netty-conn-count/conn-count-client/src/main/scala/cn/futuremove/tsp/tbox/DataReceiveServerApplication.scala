package cn.futuremove.tsp.tbox

import java.util.concurrent.{Executors, TimeUnit}

import cn.futuremove.tsp.tbox.config.{IPConfig, Ip}
import cn.futuremove.tsp.tbox.global.ChannelHolder
import cn.futuremove.tsp.tbox.netty.MyClient
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.util.CharsetUtil
import javax.annotation.PreDestroy
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

@SpringBootApplication
//@ComponentScan(basePackages = Array("cn.futuremove.tsp.tbox"))
class DataReceiveServerApplication

object DataReceiveServerApplication {

  private val logger: Logger = LoggerFactory.getLogger(classOf[DataReceiveServerApplication])
  private val buffer:ListBuffer[Unit=>Unit] = new ListBuffer[Unit => Unit]()

  @PreDestroy
  def destory() : Unit  = {
    DataReceiveServerApplication.logger.warn("System shutdown ... now")
    buffer.toList.foreach(callback => callback())
  }

  def register(callback :Unit => Unit) :Unit = {
    callback +=: buffer
  }

  def main(args: Array[String]): Unit = {

    val ctx = SpringApplication.run(classOf[DataReceiveServerApplication], args: _*)

    val ip: Ip = ctx.getBean(classOf[Ip])
    val myClient = new MyClient(ip.ip())
    for (inetPort <- 9000 to 9999) {
      for (i <- 0 to 10) {
        logger.info("connect => {}:{}", ip.ip(), inetPort)
        myClient.connect(inetPort)
      }
    }

    val executor = Executors.newSingleThreadExecutor()

    executor.execute(new Runnable {

      @tailrec
      def send(channel:Channel, idle: Long): Unit = {

        if (channel.isWritable) {
          TimeUnit.SECONDS.sleep(idle)
          logger.info("idle ==> {}", idle.asInstanceOf[AnyRef])
          val byteBuf = Unpooled.directBuffer()
          byteBuf.writeCharSequence( "DataSet API 的编程", CharsetUtil.UTF_8 )
          channel.writeAndFlush(byteBuf)
          send(channel, idle + 1)
        } else {
          logger.warn("退出")
        }

      }
      override def run(): Unit = {
        val channelOpt = ChannelHolder.getChannel("chengchao")

        channelOpt match {
          case Some(channel) =>
            //send(channel, 1L)
          case None => println("kao")
        }
      }
    })

  }
}