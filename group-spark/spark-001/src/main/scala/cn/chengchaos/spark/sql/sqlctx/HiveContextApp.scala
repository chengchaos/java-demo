package cn.chengchaos.spark.sql.sqlctx

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/7 16:09 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */

object HiveContextApp {


  def main(args: Array[String]): Unit = {


    val conf = new SparkConf()
    //conf.setMaster("local[2]").setAppName("HiveContextApp")

    val sc: SparkContext = new SparkContext(conf)

    val hiveContext:HiveContext = new HiveContext(sc)

    hiveContext.tables("hive_wordcount").show()

    sc.stop()
  }

}
