package vip.chengchao.lsn001

import java.nio.file.Paths

import org.apache.flink.api.scala.ExecutionEnvironment

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题
  * </p>
  *
  * @author chengchao - 2019-04-13 13:20 <br />
  * @since [产品模块版本]
  * @see [相关类方法]
  *
  */
object ScalaWordCoundApp {


  def main(args:Array[String]): Unit = {

    val path = Paths.get("src/test/resources/wc.txt")
      .toUri.toString

    val env:ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    val text = env.readTextFile(path)
    text.print()

    import org.apache.flink.api.scala._

    text.flatMap(_.toLowerCase.split("\\s"))
      .filter(_.nonEmpty)
      .map((_, 1))
      .groupBy(0)
      .sum(1)
      .print()


  }

}
