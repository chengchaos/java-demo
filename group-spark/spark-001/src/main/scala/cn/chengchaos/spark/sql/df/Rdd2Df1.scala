package cn.chengchaos.spark.sql.df

import org.apache.spark.sql.SparkSession

/**
  * <p>
  * <strong>
  * RDD 转换 DataFrame 的一种方法
  *
  * </strong><br /><br />
  * 如题。
  * 使用反射的方式。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/8 14:26 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object Rdd2Df1 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("rdd 2 data frame 1")
      .master("local[*]")
      .getOrCreate()

    val rdd = spark.sparkContext.textFile("src/test/resources/info.txt")

    import spark.implicits._

    val df = rdd.map(line => line.split(","))
      .map(arr => Info(arr(0).toInt, arr(1), arr(2).toInt))
      .toDF()

    df.show()

    df.createOrReplaceTempView("infos")

    spark.sql("select name, age from infos ")
      .map(x => "name : " + x(0))
      .show()


    spark.stop()
  }
  case class Info(id: Int, name: String, age: Int)
}


