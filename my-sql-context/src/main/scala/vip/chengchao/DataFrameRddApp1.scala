package vip.chengchao

import java.nio.file.Paths

import org.apache.spark.rdd.RDD
import org.apache.spark.sql
import org.apache.spark.sql.SparkSession

object DataFrameRddApp1 {


  def main(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .appName("DataFrameRddApp1")
      .master("local[*]")
      .getOrCreate()

    // RDD ==> DataFrame

    val filePath: String = Paths.get("src/test/sources/infos.txt")
        .toUri
        .toString

    val infos:RDD[String] = spark.sparkContext.textFile(filePath)

    val persons:RDD[Person] = infos.map(_.split(","))
        .map(arr => Person(arr(0).toInt, arr(1), arr(2).toInt))

    import spark.implicits._

    val df: sql.DataFrame = persons.toDF()

    df.cache()

    df.filter(df.col("age") > 30).select("name")
        .show()

    df.createOrReplaceTempView("infos")

    spark.sql("select name from infos where age > 30")
        .show()


    spark.close()
  }

  case class Person(id:Int, name:String, age:Int)

}
