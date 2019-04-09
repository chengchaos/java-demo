package vip.chengchao

import org.apache.spark.rdd.RDD
import org.junit.Test

class MyTest1 extends SparkContextTest {


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

}
