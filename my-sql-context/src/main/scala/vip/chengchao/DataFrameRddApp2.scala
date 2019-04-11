package vip.chengchao

import java.nio.file.Paths

import org.apache.spark.rdd.RDD
import org.apache.spark.sql
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object DataFrameRddApp2 {


  def main(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .appName("DataFrameRddApp2")
      .master("local[*]")
      .getOrCreate()

    // RDD ==> DataFrame

    val filePath: String = Paths.get("src/test/sources/infos.txt")
        .toUri
        .toString

    val infos:RDD[String] = spark.sparkContext.textFile(filePath)

    val persons:RDD[Row] = infos
        .map(_.split(","))
        .map(arr => Row(arr(0).toInt, arr(1), arr(2).toInt))

    val schema:StructType = StructType(StructField("id", IntegerType)
      :: StructField("name", StringType)
      :: StructField("age", IntegerType)
      :: Nil)

    val df: sql.DataFrame = spark.createDataFrame(persons, schema)


    df.cache()

    df.filter(df.col("age") > 30).select("name")
        .show()

    df.createOrReplaceTempView("infos")

    spark.sql("select name from infos where age > 30")
        .show()


    spark.close()
  }



}
