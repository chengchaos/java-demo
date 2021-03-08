package luxe.chaos.helper.log

import java.util

import org.junit.{Assert, Test}
import org.slf4j.{Marker, MarkerFactory}

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2020/12/1 21:06 <br />
 * @since [ 产品模块版本 ]
 * @see [ 相关类方法 ]
 *
 */
class LoggerTest {

  private val logger: Logger = Logger[LoggerTest]

  private val marker: Marker = MarkerFactory.getMarker("test_marker")

  private def getName(): String = {
    println("========")
    "chengchao"
  }

  @Test
  def testInfo(): Unit = {

    val name: String = "chengchao"
    val age: Int = 45
    val prop: java.util.Map[String, String] = new util.HashMap[String, String]()
    prop.put("money", "Rich")

    logger.trace {
      () => getName() + "trace " + getName()
    }
    logger.info {
      "get name => hello" + getName()
      13
    }
    logger.info("hello => {}", name)

    logger.info("hello {}, your age is {}, props => {}, int => {} , boolean => {}",
      name, age, prop, 43, true)

    logger.trace {
      val x = 1
      val y = 2
      val z = 3
      println("++++")
      s" x + y is ($x + $y == $z)"
      13
    }
    logger.info {
      val x = 1
      val y = 2
      val z = 3
      println("++++")
      s" x + y is ($x + $y == $z)"
    }

    logger.info(marker, "hello {}", "world")
    Assert.assertTrue("is OK.", true)

  }

}
