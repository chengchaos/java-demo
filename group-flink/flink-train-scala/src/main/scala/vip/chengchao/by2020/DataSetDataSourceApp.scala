package vip.chengchao.by2020

import java.nio.file.{FileSystem, FileSystems, Path, Paths}

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment, createTypeInformation}

object DataSetDataSourceApp {


  def fromCollection(env: ExecutionEnvironment): Unit = {

    import org.apache.flink.api.scala._

    val data: Seq[Int] = 0 until 10
    env.fromCollection(data).print()

    val dataSet: DataSet[Int] = env.fromCollection(1 to 10)
    dataSet.print()
    println(dataSet.getClass)


  }


  def textFile(env: ExecutionEnvironment): Unit = {

    val path = Paths.get("src/test/resources/wc.txt").toUri.toString
    println("path =>", path)

    val current: Path = FileSystems.getDefault.getPath("src/test/resources/wc.txt")
    val dataSet: DataSet[String] = env.readTextFile(current.toUri.toString)
    dataSet.print()


  }

  def csvFile(env: ExecutionEnvironment): Unit = {

    println("*** csvFile begin ***")
    val path = Paths.get("src/test/resources/people.csv")

    val dataSet: DataSet[(String, Int, String)] = env.readCsvFile[(String, Int, String)](path.toUri.toString, ignoreFirstLine = true)

    dataSet.print()
    println("*** csvFile end ***")
  }

  def mapPartitionDemo(env: ExecutionEnvironment): Unit = {

    println("*** mapPartitionDemo begin ***")
    val dataSet = env.fromCollection(1 to 100)
      .setParallelism(16)
      .map(_ + 1)
      .mapPartition(x => {
        val conn = FakeDbUtil.getConnection()
        FakeDbUtil.releaseConnection(conn)
        x
      })

    dataSet.print()
    println("*** mapPartitionDemo end ***")

  }

  def main(args: Array[String]): Unit = {


    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    //    this.fromCollection(env)
    //    this.textFile(env)
    //    csvFile(env)
    mapPartitionDemo(env)
  }

}
