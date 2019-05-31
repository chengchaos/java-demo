package cn.futuremove.tsp.tbox

import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

import cn.futuremove.tsp.tbox.global.ChannelHolder
import cn.futuremove.tsp.tbox.netty.MyClient
import cn.futuremove.tsp.tbox.netty.server.GatewayServer
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.util.CharsetUtil
import javax.annotation.PreDestroy
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

@SpringBootApplication
@ComponentScan(basePackages = Array("cn.futuremove.tsp.tbox"))
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

    SpringApplication.run(classOf[DataReceiveServerApplication], args: _*)

    val myClient = new MyClient()
    for (inetPort <- 9000 to 9999) {

      for (i <- 1 to 10) {
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