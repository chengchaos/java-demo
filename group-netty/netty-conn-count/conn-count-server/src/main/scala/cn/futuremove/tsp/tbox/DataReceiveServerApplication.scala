package cn.futuremove.tsp.tbox

import java.util.concurrent.{Executors, TimeUnit}

import cn.futuremove.tsp.tbox.global.ChannelHolder
import cn.futuremove.tsp.tbox.netty.server.MyServer
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
  def destroy() : Unit  = {
    DataReceiveServerApplication.logger.warn("System shutdown ... now")
    buffer.toList.foreach( cb => cb() )
  }

  def register(callback :Unit => Unit) :Unit = {
    callback +=: buffer
  }

  def main(args: Array[String]): Unit = {

    SpringApplication.run(classOf[DataReceiveServerApplication], args: _*)

    for (port <- 9000 to 9999) {
      new MyServer().start(port)
    }

    val executor = Executors.newSingleThreadExecutor()
    executor.submit(new Runnable() {
      @tailrec def loop(): Unit = {
        val underlyingKeys = ChannelHolder.underlyingKeys
        logger.info("当前连接数 ==> {}", underlyingKeys.size.asInstanceOf[AnyRef])
        TimeUnit.SECONDS.sleep(2L)
        loop()
      }
      override def run(): Unit =  loop()
    })
    register(_ => {
      logger.info("................ do in callback ................")
    })
  }
}