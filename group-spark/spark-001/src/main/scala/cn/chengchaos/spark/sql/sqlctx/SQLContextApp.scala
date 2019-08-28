package cn.chengchaos.spark.sql.sqlctx

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * <p>
  * <strong>
  * SQLContext 的使用
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/8/7 15:16 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object SQLContextApp {

  def main(args: Array[String]): Unit = {


    val path = if (args.nonEmpty) {
      args(0)
    } else {
      "src/test/resources/people.json"
    }


    // 1: 创建相应的 Context
    val conf = new SparkConf()
    // 线上环境中 app name 和 master
    conf.setAppName("SQLContextApp").setMaster("local[2]")

    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    // 2: 业务处理

    sqlContext.read
        .format("json")
        .load(path)
        .show()


    // 3： 关闭
    sc.stop()
  }
}
