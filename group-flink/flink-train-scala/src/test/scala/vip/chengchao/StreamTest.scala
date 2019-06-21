package vip.chengchao

import java.util.concurrent.TimeUnit

import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.junit.Test
import vip.chengchao.StreamTestMain.MySourceFunction

import scala.annotation.tailrec

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 5:28 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class StreamTest {

  import org.apache.flink.api.scala._

  @Test def test00: Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val text = env.socketTextStream("192.168.88.174", 9999)


    val counts = text.flatMap(_.toLowerCase.split("\\W+"))
      .map((_, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counts.print()
    env.execute("window stream word count")

  }



  @Test def testt002 = {


    val data = StreamExecutionEnvironment.getExecutionEnvironment
      .addSource(new MySourceFunction())
      .setParallelism(2)

    data.print().setParallelism(1);

    ()
  }

}
