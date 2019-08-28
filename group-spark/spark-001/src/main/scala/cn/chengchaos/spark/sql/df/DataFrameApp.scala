package cn.chengchaos.spark.sql.df

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/8 14:02 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object DataFrameApp {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("DataFrameApp")
      .master("local[*]")
      .getOrCreate()

    val path = "src/test/resources/people.json"

    val dataFrame: DataFrame = spark.read.format("json")
      .load(path)

    // 打印 Schema
    dataFrame.printSchema()
    // 显示数据（默认 20 条）
    dataFrame.show(20)

    // 选择列
    dataFrame.select("name").show()

    // 选择列
    dataFrame
      // 过滤
      .filter(dataFrame.col("age") > 19)
      .select(
        dataFrame.col("name"),
        (dataFrame.col("age") + 10).as("虚岁"))
      .show(10)

    // 分组
    dataFrame.groupBy(dataFrame.col("age"))
        .count()
        .show()
    spark.stop()
  }

}
