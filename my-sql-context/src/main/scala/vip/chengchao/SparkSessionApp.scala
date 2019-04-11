package vip.chengchao

import java.nio.file.Paths

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession

object SparkSessionApp {

  def main(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .appName("SparkSessionApp")
      .getOrCreate()

    val filePath : String = Paths
      .get("src/test/sources/tweets.json")
      .toUri
        .toString

    println(s"absolutePath ==> $filePath")

    val data: sql.DataFrame = spark.read
      .format(source = "json")
      .load(path = filePath)

    data.cache()

    data.printSchema()
    data.show(5)

    data.select("name", "age", "name").show()

    data.select(data.col("name"),
      (data.col("age") + 10).as("new_age"))
        .show()

    data.filter(data.col("age") > 42)
        .show()

    data.createOrReplaceTempView("tweets")

    spark.sql(
      """
        |select age, count(age) c_age from tweets
        |group by age
      """.stripMargin)
        .show()
    spark.close()
  }
}
