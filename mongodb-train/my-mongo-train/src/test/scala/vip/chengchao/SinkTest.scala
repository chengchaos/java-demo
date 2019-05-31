package vip.chengchao

import java.io.File

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.junit.Test
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 3:55 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class SinkTest {


  @Test
  def test01: Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val range = 1 to 10
    val data = env.fromCollection(range)

    val filePath = "file:///E:/temp/flink-output/test01.txt"

    data.writeAsText(filePath, FileSystem.WriteMode.OVERWRITE)
      .setParallelism(2)

    env.execute("test01")
  }

  @Test
  def counterAppTest = {


    val filePath = "file:///E:/temp/flink-output/test01.txt"


    val richMapFunction = new RichMapFunction[String, String]() {

      // step1: 定义计数器
      var counter = new LongCounter()

      // step2: 注册计数器
      override def open(parameters: Configuration): Unit = {
        getRuntimeContext.addAccumulator("counterAppTest", counter)
      }

      override def map(value: String): String = {
        counter add 1
        value
      }
    }

    val env = ExecutionEnvironment.getExecutionEnvironment

    val data = env.fromElements("hadoop", "spark", "flank", "hbase", "elasticsearch")
    data.map(richMapFunction)

      .writeAsText(filePath, FileSystem.WriteMode.OVERWRITE)
      .setParallelism(3)

    val executionResult = env.execute("counterAppTest")

    val result: Long = executionResult.getAccumulatorResult[Long]("counterAppTest")

    println(result)

  }

  /**
    * 分布式缓存
    *
    * 必须指定一个名字。
    *
    *
    */
  @Test def distributedCacheTest = {


    val env = ExecutionEnvironment.getExecutionEnvironment

    // 注册一个缓存
    val filePath = "file:///e:/temp/mycsv.csv"
    val cacheName = "my-scala-dc"

    env.registerCachedFile(filePath, cacheName)

    val richMapFunction = new RichMapFunction[String, String] {


      override def open(parameters: Configuration): Unit = {
        val dcFile: File = getRuntimeContext.getDistributedCache.getFile(cacheName)
        val strings = FileUtils.readLines(dcFile)
        import scala.collection.JavaConversions._
        strings.foreach(println)
      }

      override def map(value: String): String = {
        value
      }
    }

    val data1 = env.fromElements("hadllp", "spark", "flink", "hbase", "elasticsearch", "redis",
      "hadllp", "spark", "flink", "hbase", "elasticsearch", "redis")

    val data2 = env.fromElements(1, 2, 3)

//    data1.cross(data2).print()

    data1.map((_, 1))
      .groupBy(0)
      .sum(1)
      .print()

//    data1.map(richMapFunction).print()

    //env.execute("distributedCacheTest")

    ()
  }


}
