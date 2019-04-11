package vip.chengchao

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

object HiveContextApp {

  def main(args: Array[String]) :Unit = {

    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("HiveContextApp")

    val sparkContext = new SparkContext(sparkConf)


    val hiveContext = new HiveContext(sparkContext)

    hiveContext.table("emp").show

    sparkContext.stop()
  }

}
