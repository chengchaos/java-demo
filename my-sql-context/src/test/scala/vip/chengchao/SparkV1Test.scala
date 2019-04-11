package vip.chengchao

import org.apache.spark.rdd.RDD
import org.apache.spark.sql
import org.apache.spark.sql.Row
import org.junit.Test

class SparkV1Test extends SparkContextTest {


  @Test
  def testWordCount(): Unit = {

    val inputFile = "e:/temp/session.log"
    val outputFile = "e:/temp/session.log.count"

    this.withSparkContext(sc => {
      val input = sc.textFile(inputFile)
      val words = input.flatMap(line => line.split(" "))
      val counts = words.map(word => (word, 1))
        .reduceByKey {
          case (x, y) => x + y
        }
      counts.saveAsTextFile(outputFile)
    })

  }

  @Test
  def reduceRest(): Unit = {

    this.withSparkContext {
      sc => {
        val rdd = sc.parallelize(List(1, 2, 3, 4, 5))

        val rddType = rdd.persist()

        logger.info("rdd.type ==> {}", rddType)

        val sum1 = rdd.reduce((x, y) => x + y)
        logger.debug("sum1 ==> {}", sum1)

        val sum2 = rdd.fold(0)((x, y) => x + y)
        logger.debug("sum2 ==> {}", sum2)
      }

    }

  }

  @Test
  def pairRddTest(): Unit = {

    val lines: List[String] = List("Hello world", "Hello China")
    val pairs: List[(String, Int)] = lines.flatMap(x => x.split(" "))
      .map(x => (x, 1))

    this.withSparkContext(sc => {
      val rdd: RDD[(String, Int)] = sc.parallelize(lines)
        .flatMap(x => x.split(" "))
        .map(x => (x, 1))
        .reduceByKey((x, y) => x + y)

      if (logger.isDebugEnabled) {
        logger.debug("rdd.toDebugString ==> {}", rdd.toDebugString)
      }
      //val array = rdd.collect()

      rdd.collect()
        .foreach(p => logger.debug(" {} ==> {}", p._1, p._2))

    })

  }

  @Test
  def testSqlCtx001(): Unit = {

    val cb = new SqlCtx001().callback("e:/temp/people.json")

    this withSqlContext cb
  }


  val path = "file:///D:\\Books\\ituring.com.cn\\Spark-高级数据分析_v2\\block_1.csv"

  @Test
  def testCvs() : Unit = {

    def isHead(line:String) = line.contains("id_1")

    this.withSparkContext(callback = sc => {
      val rawblocks = sc.textFile(path)

      val data = rawblocks.filter(!isHead(_))
      val head = data.take(10)
      head.foreach(println)

    })
  }

  @Test
  def testDataFrame(): Unit = {

    this.withSparkSession(callback = spark => {
      val parsed:sql.DataFrame = spark
        .read
        .option("header", "true")
        .option("nullValue", "?")
        .option("inferSchema", "true")
        .format("csv")
        .load(path)
      //      parsed.show()
      parsed.cache()

      parsed.printSchema()

      val count = parsed.count()
      logger.info("count ==> {}", count)

      val rdd:RDD[Row] = parsed.rdd

      val map = rdd
        .map(_.getAs[Boolean]("is_match"))
        .countByValue()

      logger.info("map ==> {}", map)

      // For implicit conversions like converting RDDs to DataFrames
      import spark.implicits._

      parsed.groupBy("is_match").count()
        .orderBy($"count".desc)
        .show()

      println("============")

      parsed.createOrReplaceTempView("linkage")



      spark.sql(
        """
          select is_match, count(*) cnt
          from linkage
          group by is_match
          order by cnt desc
        """.stripMargin)
        .show()
    })
  }
}
