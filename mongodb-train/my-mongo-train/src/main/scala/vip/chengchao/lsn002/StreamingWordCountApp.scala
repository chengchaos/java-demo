package vip.chengchao.lsn002

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题
  * </p>
  *
  * @author chengchao - 2019-04-13 15:23 <br />
  * @since [产品模块版本]
  * @see [相关类方法]
  *
  */
object StreamingWordCountApp {


  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment= StreamExecutionEnvironment
      .getExecutionEnvironment

    val text = env.socketTextStream("localhost", 9999)

    import org.apache.flink.api.scala._

    text.flatMap(_.toLowerCase.split("\\s"))
      .filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print()
      .setParallelism(1)

    env.execute()

  }

}
