package vip.chengchao

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.slf4j.{Logger, LoggerFactory}

class SqlCtx001 {


  val logger: Logger = LoggerFactory.getLogger("vip.chengchao.SqlCtx001")

  def callback(path: String): SQLContext => Unit = sqlContext => {

    val people:DataFrame = sqlContext.read
      .format("json")
      .load(path)

    logger.info("print schema ++++++++++++++++++++ ")
    people.printSchema()
    logger.info("print show  ++++++++++++++++++++ ")
    people.show()

  }

}
