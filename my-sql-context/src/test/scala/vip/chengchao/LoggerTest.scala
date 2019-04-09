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

}
