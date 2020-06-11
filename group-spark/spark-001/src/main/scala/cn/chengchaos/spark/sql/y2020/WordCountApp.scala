package cn.chengchaos.spark.sql.y2020

import grizzled.slf4j.Logger
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/6/8 11:51 <br />
 * @since [产品模块版本]
 * @see [相关类方法]
 *
 */

class WordCountApp(val localDev: Boolean) {


  // 惰性变量只能是不可变变量，并且只有在调用惰性变量时，才会去实例化这个变量。
  private lazy val sparkSession: SparkSession = {

    WordCountApp.logger.info("*** lazy ***")
    val builder = SparkSession.builder()
    if (localDev) {
      builder.appName("WordCountApp").master("local[*]")
    }
    builder.getOrCreate()
  }

  def init(): WordCountApp = {
    this
  }

  def execute(fn: SparkSession => Unit): Unit = {
    try {
      fn(sparkSession)
    } finally {
      if (sparkSession != null) {
        sparkSession.stop()
      }
    }
  }
}


object WordCountApp {

  val logger: Logger = Logger(classOf[WordCountApp])

  import org.apache.spark.SparkContext._

  def main(args: Array[String]): Unit = {

    logger.info("*** Begin *** ")

    var path = "";
    var localDev = false;
    if (args(0) != null && args(0).equals("dev")) {
      path = "file:///Users/chengchao/git/github.com/java-demo/group-spark/spark-001/src/test/data/origin-2020060811.log"
      localDev = true
    } else {
      path = args(0)
    }

    logger.info(s"path => $path, localDev => $localDev")

    val wca = new WordCountApp(localDev)

    wca.execute(sparkSession => {
      val rdd: RDD[String] = sparkSession.read
        .format("text")
        .load(path)
        .rdd
        .filter(row => row != null)
        .map(f => f.toString())
        .filter(str => str.length > 0)

      // PairRDD
      // reduceByKey(func) reduceByKey((x, y) => x + y)
      // gorupByKey()
      // combineByKey
      // mapValues(func) 对每个值应用一个函数， 不改变 key : mapValue(x => x + 1)
      // flatMapValues(func) flatMapValues(x => (x to 5))
      // keys 返回一个仅仅包含 key 的 RDD
      // values
      // sortByKey() 返回一个根据 key 排序的 RDD
      // **ACTION**
      // countByKey()
      // collectAsMap()
      // lookup(key)
      val rdd2: RDD[(String, Int)] = rdd.map(line => {
        val index = line.indexOf("{")
        val vin = line.substring(index + 8, index + 8 + 17)
        (vin, 1)
      })
        .persist(StorageLevel.MEMORY_ONLY_2)
      rdd2.reduceByKey(_ + _)

      if (localDev) {
        rdd2.take(10).foreach(println)
      } else {
        rdd2.saveAsTextFile(args(1))
      }

      val count = rdd2.count()
      logger.info(s"*** count => $count ***")

      val count2 = rdd2.keys.count()
      logger.info(s"*** count2 => $count2 ***")

    })
  }
}
