package vip.chengchao

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.slf4j.LoggerFactory

/**
  * SQLContext 的使用
  */
object SqlContextApp {

  val LOGGER = LoggerFactory.getLogger("vip.chengchao.SqlContextApp")

  def main(args: Array[String]) :Unit = {

    LOGGER.info("args.lenght ==> {}", args.length)

    if (args.length ==0) {
      println("请指定文件路径")
      System.exit(1)

    }

    execute(args(0))

  }


  def execute(path:String) :Unit = withSqlContext(new SqlCtx001().callback(path))


  def withSqlContext(callback: SQLContext => Unit) :Unit = {

    /* 创建一个 SparkConf */
    // 在测试或者生产中, AppName 和 Master 是通过脚本进行指定
    val sparkConf = new SparkConf()

    //.setAppName("SQLContextDemo")
    //.setMaster("local[2]")

    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

    /* 使用 SparkConf 创建 SparkContext */
    val sparkContext = new SparkContext(sparkConf)

    /* 使用 SparkContext 创建相应的 Context */
    val sqlContext = new SQLContext(sparkContext)


    callback(sqlContext)

    /* 关闭资源 */
    sparkContext.stop()
  }


  def run(sqlContext: SQLContext) :Unit = {

    val people:DataFrame = sqlContext.read
      .format("json")
      .load("e:/temp/minan-realtime-data-001.json")

    LOGGER.info("print schema ==================>")
    people.printSchema()
    LOGGER.info("print show =>>>>>>>>>>>>>>>>>")
    people.show()
  }
}
