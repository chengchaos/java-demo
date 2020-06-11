package cn.chengchaos.spark.sql.y2020

import com.mongodb.client.model.{Aggregates, Field}
import com.mongodb.spark.{MongoConnector, MongoSpark}
import com.mongodb.spark.config.{ReadConfig, WriteConfig}
import com.mongodb.spark.rdd.MongoRDD
import grizzled.slf4j.Logger
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.bson.Document

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
class RwMongoApp2 {


}

object RwMongoApp2 {


  val logger: Logger = Logger(classOf[RwMongoApp2])

  def main(args: Array[String]): Unit = {

    logger.info("*** RwMongoApp2 now starting ***")

    // 更多配置参考：
    // https://docs.mongodb.com/spark-connector/master/configuration/
    var sparkSession:SparkSession = SparkSession.builder()
      .master("local")
      .appName("RwMongoApp")
      .config("spark.mongodb.input.uri", "mongodb://127.0.0.1:27017/chaos.cheng")
      .config("spark.mongodb.output.uri", "mongodb://127.0.0.1:27017/chaos.data")
      .getOrCreate()

    val sc = sparkSession.sparkContext

    val wc: WriteConfig = WriteConfig.create(sc)
    val rc: ReadConfig = ReadConfig.create(sc)

    val pipeline = ArrayBuffer(
      /// $month 可以把 birthday 中的月份取出来
            Aggregates.addFields(new Field("month", new Document("$month", "$birthday")))
    )

    // 使用 MongoSpark.load 读取数据
    val mongoRdd:MongoRDD[Document] = MongoSpark.load(sc, rc) .withPipeline(pipeline)

    val ds: Dataset[Row] = mongoRdd.toDF()
    ds.createOrReplaceTempView("User")

    val result: Dataset[Row] = sparkSession.sql("select * from User")

    result.show()



//    rdd.foreachPartition( iterator => {
//      var mc: MongoConnector = MongoConnector.create(sc)  // create(wc.asOptions)
//      mc.withCollectionDo(rc, classOf[Document], collection => {
//        while (iterator.hasNext) {
//          val doc = iterator.next()
//          collection.replaceOne(eq("xx", ""), doc)
//        }
//
//      })
//    })


    // 使用 MongoSpark.save 写回 mongo
    // 如果 _id 存在就替换； 不存在就创建
//    MongoSpark.save(rdd)


    sparkSession.stop()
  }
}



