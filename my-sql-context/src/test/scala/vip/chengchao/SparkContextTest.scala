package vip.chengchao

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}


class SparkContextTest extends Serializable {

  val logger: Logger = LoggerFactory.getLogger(classOf[SparkContextTest])

  def withSparkContext(callback: SparkContext => Unit): Unit = {

    val conf = new SparkConf()
      .setAppName("SparkContextTest")
      .setMaster("local[*]")

    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    // 严格要求注册类
    //conf.set("spark.kryo.registrationRequired", "true")
    //conf.registerKryoClasses(Array(classOf[MyClass], classOf[MyOtherClass]))
    conf.registerKryoClasses(Array(classOf[SparkV1Test]))

    // 在 VM.Options 中增加 ： -ea -Dspark.master=local[*]
    // conf.setMaster("local[2]")

    val sparkContext = new SparkContext(conf)

    callback(sparkContext)

    sparkContext.stop()

  }

  def withSqlContext(callback: SQLContext => Unit): Unit = {

    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("SparkContextTest")

    val sparkContext = new SparkContext(sparkConf)
    // @deprecated("Use SparkSession.builder instead", "2.0.0")
    val sqlContext = new SQLContext(sparkContext)

    callback(sqlContext)

    sparkContext.stop()

  }


  def withHiveContext(callback: HiveContext => Unit): Unit = {

    val sparkConf = new SparkConf()
    sparkConf.setAppName("SparkContextTest")
    sparkConf.setMaster("local[2]")

    val sparkContext = new SparkContext(sparkConf)

    // @deprecated("Use SparkSession.builder.enableHiveSupport instead", "2.0.0")
    val hiveContext = new HiveContext(sparkContext)

    callback(hiveContext)

    sparkContext.stop()

  }

  def withSparkSession(callback:SparkSession => Unit): Unit = {

    val spark:SparkSession = SparkSession.builder()

      .master("local")
      .appName("SparkContextTest")
      //.config("spark.some.config.option", "some-value")
      .getOrCreate()


    callback(spark)

    spark.close()
  }
}
