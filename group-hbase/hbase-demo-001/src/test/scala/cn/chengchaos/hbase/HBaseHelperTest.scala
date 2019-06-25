package cn.chengchaos.hbase

import cn.chengchaos.hbase.HBaseHelper.createTable
import org.apache.hadoop.hbase.client.{Result, ResultScanner}
import org.apache.hadoop.hbase.util.Bytes
import org.junit.Test

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题
  * </p>
  *
  * @author chengchao - 2019-06-24 12:58 <br />
  * @since [产品模块版本]
  * @see [相关类方法]
  *
  */
class HBaseHelperTest {


  @Test
  def createTableTest() : Unit = {

    val res = createTable("mytest", Array("cf"))
    println(s"res = $res ")
  }


  @Test
  def retrieveTableTest() : Unit = {
    val table = HBaseHelper.getTable("test")
    println("table == "+ table)
  }

  @Test
  def save1RowTest() : Unit = {
    val tabName = "mytest"
    val rowkey = "mytest0002"
    val cf = "cf"
    val qualifier = "username"
    val v = "火🔥龙果"

    val x = HBaseHelper.put1Cell(tabName, rowkey, cf, qualifier, v)

    println("x = "+ x)
  }

  @Test
  def retrieve1RowTest() : Unit = {

    val resultOpt = HBaseHelper.resultOption("mytest", "mytest0002")
    println("result  = "+ resultOpt)

    if (resultOpt.isDefined) {
      val result = resultOpt.get
      val byteValue  = result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("username"))
      val username = Bytes.toString(byteValue)
      println(s"username ==> $username")
    }

    HBaseHelper.closeConnection()

  }

  @Test
  def scannerTest() : Unit = {

    val scannerOption = HBaseHelper.resultScannerOption("mytest")
    if (scannerOption.nonEmpty) {

      val scannerResult: ResultScanner= scannerOption.get
      import scala.compat.java8.FunctionConverters._

      val action : Result => Unit = r => {
        println(r)
        val value = Bytes.toString(r.value())
        println(s"value ==> $value")
      }

      scannerResult.forEach(action.asJava)

    }

  }
}
