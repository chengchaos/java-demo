package cn.chengchaos.spark.sql.sqlctx

import org.apache.spark.sql.SparkSession

/**
  * <p>
  * <strong>
  * Spark Session 的使用
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/7 17:08 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object SparkSessionApp {


  def main(args: Array[String]): Unit = {


    val path = if (args.nonEmpty) {
      args(0)
    } else {
      "src/test/resources/people.json"
    }


    val spark = SparkSession.builder()
      .appName("SparkSessionApp")
      .master("local[2]")
      .getOrCreate()

    spark.read
      .format("json")
      .load(path)
      .printSchema()


    spark.stop()

  }

}
