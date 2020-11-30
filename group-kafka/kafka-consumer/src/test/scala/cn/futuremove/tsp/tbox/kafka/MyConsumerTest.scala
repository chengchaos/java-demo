package cn.futuremove.tsp.tbox.kafka

import java.util.concurrent.TimeUnit
import java.util.function.Supplier

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.junit.Test

class MyConsumerTest extends LazyLogging {


  @Test
  def runTest() : Unit = {

    val myConsumer = new MyConsumer("192.168.88.21:9092", "test001", "test")
    myConsumer.execute(1)

    TimeUnit.MINUTES.sleep(1)
    println("*** end ***")
  }

  implicit def block2runnable1(codeBlock: () => Unit) : Runnable = {
    new Runnable {
      override def run(): Unit = codeBlock()
    }
  }
  implicit def block2runnable2(codeBlock: => Unit) : Runnable = {
    new Runnable {
      override def run(): Unit = codeBlock
    }
  }

  implicit def block2Supplier[A](codeBlock: => A) : java.util.function.Supplier[A] = {
    new Supplier[A] {
      override def get(): A = codeBlock
    }
  }

  def setName(name: String, supplier: Supplier[String]) : Unit = {

    if (name != null) {
      logger.info(s"name = $name")
    } else {
      logger.info(s"name = ${supplier.get()}")
    }
  }

  @Test
  def runnableTest() : Unit = {

    var v :Int = 0
    new Thread(() => {
      for (i <- 1 until 10) {
//        logger.info(s"%d => %s", i, (i + ";"))
        v = v + i
        println("v = "+ v)
      }
    }).start()

    TimeUnit.SECONDS.sleep(3)
  }

  setName(null,  "hello 2")
}
