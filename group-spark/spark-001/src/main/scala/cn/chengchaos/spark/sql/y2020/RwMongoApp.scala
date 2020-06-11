package cn.chengchaos.spark.sql.y2020

import java.util

import com.mongodb.BasicDBObject
import com.mongodb.client.model.Field
import com.mongodb.spark.MongoSpark
import grizzled.slf4j.Logger
import org.apache.spark.sql.SparkSession
import org.bson.Document
import org.bson.conversions.Bson
import org.spark_project.jetty.util.Fields

import scala.collection.mutable.ArrayBuffer

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/6/8 15:23 <br />
 * @since [产品模块版本]
 * @see [相关类方法]
 *
 */
class RwMongoApp {


}


object RwMongoApp {

  val logger: Logger = Logger(classOf[RwMongoApp])

  def main(args: Array[String]): Unit = {

    logger.info("*** now starting ***")

    // 更多配置参考：
    // https://docs.mongodb.com/spark-connector/master/configuration/
    var sparkSession:SparkSession = SparkSession.builder()
      .master("local")
      .appName("RwMongoApp")
      .config("spark.mongodb.input.uri", "mongodb://127.0.0.1:27017/chaos.cheng")
      .config("spark.mongodb.output.uri", "mongodb://127.0.0.1:27017/chaos.data")
      .getOrCreate()

    val sc = sparkSession.sparkContext
    val pipeline = ArrayBuffer(
//      BasicDBObject.addFields(new Field("month", new Document("$mothh", "$birthday")))
    )

    // 使用 MongoSpark.load 读取数据
    val rdd = MongoSpark.load(sc)

        .withPipeline(pipeline)

    rdd.foreach(println)

    // 使用 MongoSpark.save 写回 mongo
    // 如果 _id 存在就替换； 不存在就创建
    MongoSpark.save(rdd)

    sparkSession.stop()
  }
}
