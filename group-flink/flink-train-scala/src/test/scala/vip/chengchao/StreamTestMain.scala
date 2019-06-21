package vip.chengchao

import java.util.concurrent.TimeUnit

import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

import scala.annotation.tailrec

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 11:05 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object StreamTestMain {



  import org.apache.flink.api.scala._

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data = env
      .addSource(new MySourceFunction())
      .setParallelism(2)

    data.print().setParallelism(1)

    env.execute("StreamTestMain")

  }


  class MySourceFunction extends RichParallelSourceFunction[Long] {

    @volatile var isRun = true
    val total = 10


    override def run(ctx: SourceFunction.SourceContext[Long]): Unit = {


      @tailrec
      def exec(count: Int): Unit = {

        if (count > total) {
          this.cancel()
        }

        if (isRun) {
          ctx.collect(count)
          TimeUnit.SECONDS.sleep(1L)
          exec(count + 1)

        }
      } // exec end

      exec(1)
    }

    override def cancel(): Unit = {
      isRun = false
    }
  }
}
