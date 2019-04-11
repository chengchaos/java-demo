package vip.chengchao

import java.io.File

import org.apache.spark.sql
import org.apache.spark.sql.Dataset
import org.junit.Test

class HiveContextTest extends SparkContextTest {





  @Test
  def readJsonTest() :Unit = {


    val file = new File("src/test/sources/tweets.json")
    val filePath = file.getAbsolutePath

    logger.info("filePath ==>{}", filePath)

    this.withHiveContext(hiveContext => {
      // @deprecated("Use read.json() instead.", "1.4.0")
      val input:sql.DataFrame = hiveContext.jsonFile(filePath)
      // 注册输入的 DataFrame
      // @deprecated("Use createOrReplaceTempView(viewName) instead.", "2.0.0")
      input.registerTempTable("tweets")

      // 统计
      val topTweets:sql.DataFrame= hiveContext.sql("select * from tweets")

      topTweets.printSchema()

      topTweets.show()


    })
  }

}
