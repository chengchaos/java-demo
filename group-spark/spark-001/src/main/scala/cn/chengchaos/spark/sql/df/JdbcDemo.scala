package cn.chengchaos.spark.sql.df

import org.apache.spark.sql.SparkSession

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/9 13:33 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object JdbcDemo {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("jdbc demo app")
      .master("local[2]")
      .getOrCreate()

    val df = spark.read
        .format("jdbc")
        .options(Map(
          "url" -> "jdbc:mysql://hadoop:3306/chengchao?user=root&password=Charset=UTF8",
          "dbtable" -> "demos",
          "driver" -> "com.mysql.jdbc.Driver"
        ))
        .load()


//    df.show()


    df.printSchema()

    df.write
        .format("jdbc")
        .option("url", "jdbc:mysql://hadoop:3306/chengchao")
        .option("dbtable", "demos2")
        .option("user", "root")
        .option("password", "Charset=UTF8")
        .save()


    spark.stop()

  }
}
