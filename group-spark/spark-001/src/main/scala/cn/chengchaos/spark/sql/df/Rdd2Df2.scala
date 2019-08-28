package cn.chengchaos.spark.sql.df

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * <p>
  * <strong>
  * 当不能事先创建 case class 时侯。
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/8 14:51 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object Rdd2Df2 {

  /**
    * 1. Create an RDD of Rows from the origin RDD;
    * 2. Create the schema represented by a StructType matching the structure
    * of Rows in the RDD created in Step 1.
    * 3. Apply the schema to the RDD of Rows via createDataFrame method provided by SparkSession.
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("rdd to data frame 2")
      .master("local[*]")
      .getOrCreate()

    val rowRDD: RDD[Row] = spark
      .sparkContext
      .textFile("src/test/resources/info.txt")
      .map(line => line.split(","))
      .map(arr => Row(arr(0).toInt, arr(1), arr(2).toInt))


    //    val schema: StructType = new StructType()
    //        .add("id", IntegerType)
    //        .add("name", StringType)
    //        .add("age", IntegerType)

    val schema: StructType = StructType(
      StructField("id", IntegerType) ::
        StructField("name", StringType) ::
        StructField("age", IntegerType) ::
        Nil)


    val infoDF = spark.createDataFrame(rowRDD, schema)

    infoDF.filter("name <> ''")
      .sort(infoDF.col("age").desc)
      .show(10, false)

    infoDF.createOrReplaceTempView("infos")

    spark.sql("select name, age from infos ")
      .show()




    spark.stop()
  }
}
