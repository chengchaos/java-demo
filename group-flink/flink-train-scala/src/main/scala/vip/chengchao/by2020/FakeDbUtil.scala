package vip.chengchao.by2020

import scala.concurrent.forkjoin.ThreadLocalRandom

object FakeDbUtil {

  def getConnection(): String = {
    java.util.concurrent.ThreadLocalRandom.current().nextInt()
    "CONN:" + ThreadLocalRandom.current().nextInt()
  }



  def releaseConnection(conn: String): Unit = println(s"Release => $conn")
}
