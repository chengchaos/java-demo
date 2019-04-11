package vip.chengchao

import org.junit.Test
import org.slf4j.LoggerFactory

class LoggerTest {


  val logger = LoggerFactory.getLogger("LoggerTest")

  @Test
  def printLogTest() :Unit = {

    logger.error("hello error")
    logger.warn("hello warn")
    logger.info("hello info")
    logger.debug("hello debug")
    logger.trace("hello trace")

    println("== end ==")
  }

  @Test
  def printAcsii(): Unit = {

    val longVal = 32L
    val intVal = longVal.asInstanceOf[Int]

    val charVal = intVal.asInstanceOf[Char]


    val c:Char = 65

    println("c == "+ c)

    for (i <- 0 to 93) {
      logger.info("i: {} = {}",i, (i + 33).asInstanceOf[Char])
    }
  }
}
