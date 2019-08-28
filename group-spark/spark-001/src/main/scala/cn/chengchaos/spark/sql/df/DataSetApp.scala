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
  * @author chengchaos[as]Administrator - 2019/8/8 17:32 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object DataSetApp {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("data set app")
      .master("local[*]")
      .getOrCreate()


    val csvPath = "src/test/resources/sales.csv"


    val df: DataFrame = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(csvPath)

    df.show()

    import spark.implicits._
    val ds = df.as[Sales]

    ds.map(line => line.itemId).show()

    spark.stop()
  }

  case class Sales(transactionId: Int, customerId: Int, itemId: Int, amountPaid: Double)
}


