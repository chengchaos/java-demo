package cn.chengchaos.hbase

import org.apache.hadoop.hbase.client.{Result, ResultScanner}
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp
import org.apache.hadoop.hbase.filter.FilterList.Operator
import org.apache.hadoop.hbase.filter.{BinaryComparator, ColumnPrefixFilter, FilterList, KeyOnlyFilter, PrefixFilter, RowFilter}
import org.apache.hadoop.hbase.util.Bytes
import org.junit.Test

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/25 16:32 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class HBaseFilterTest {


  import scala.collection.JavaConversions._

  implicit def str2bytes(input: String): Array[Byte] = Bytes.toBytes(input)

  val tableName: String = "filter-table"

  @Test
  def createTableTest(): Unit = {
    HBaseHelper.createTable(tableName, Array("fileInfo", "saveInfo"))
  }

  def deleteTableTest(): Unit = {
    HBaseHelper.deleteTable(tableName)
  }

  @Test
  def addDataTest(): Unit = {

    HBaseHelper.put1Cell(tableName, "rowkey1", "fileInfo", "name", "girl.jpg")
    HBaseHelper.put1Cell(tableName, "rowkey1", "fileInfo", "type", "jpg")
    HBaseHelper.put1Cell(tableName, "rowkey1", "fileInfo", "size", "717136")
    HBaseHelper.put1Cell(tableName, "rowkey1", "saveInfo", "creator", "程超")

    HBaseHelper.put1Cell(tableName, "rowkey2", "fileInfo", "name", "man.jpg")
    HBaseHelper.put1Cell(tableName, "rowkey2", "fileInfo", "type", "jpg")
    HBaseHelper.put1Cell(tableName, "rowkey2", "fileInfo", "size", "717136")
    HBaseHelper.put1Cell(tableName, "rowkey2", "saveInfo", "creator", "韩冬")

    HBaseHelper.put1Cell(tableName, "rowkey3", "fileInfo", "name", "dragon.png")
    HBaseHelper.put1Cell(tableName, "rowkey3", "fileInfo", "type", "png")
    HBaseHelper.put1Cell(tableName, "rowkey3", "fileInfo", "size", "77665")
    HBaseHelper.put1Cell(tableName, "rowkey3", "saveInfo", "creator", "程超")

    HBaseHelper.put1Cell(tableName, "rowkey4", "fileInfo", "name", "money.jpg")
    HBaseHelper.put1Cell(tableName, "rowkey4", "fileInfo", "type", "jpg")
    HBaseHelper.put1Cell(tableName, "rowkey4", "fileInfo", "size", "23232")
    HBaseHelper.put1Cell(tableName, "rowkey4", "saveInfo", "creator", "高继原")

  }


  @Test
  def rowFilterTest(): Unit = {

    val filter = new RowFilter(CompareOp.EQUAL, new BinaryComparator("rowkey1"))
    val filterList: FilterList = new FilterList(Operator.MUST_PASS_ALL, filter :: Nil)

    val resultScannerOption = HBaseHelper.resultScannerOption(tableName
      , "rowkey1"
      , "rowkey3"
      , filterList)

    def resultOperation(result: Result): Unit = {
      println("rowkey = " + Bytes.toString(result.getRow))
      println("fileName = " + Bytes.toString(result.getValue("fileInfo", "name")))
    }

    resultScannerOption.foreach {
      HBaseHelper.withResultScanner(_) {
        _ foreach resultOperation
      }
    }

    if (resultScannerOption.nonEmpty) {
      HBaseHelper.withResultScanner(resultScannerOption.get) {
        rs => rs.foreach(resultOperation)
      }
    }


  }

  @Test
  def prefixFilterTest(): Unit = {


    val filter = new PrefixFilter("rowkey2")

    val filterList = new FilterList(Operator.MUST_PASS_ALL, filter :: Nil)

    val rsopt: Option[ResultScanner] = HBaseHelper.resultScannerOption(tableName,
      "rowkey1",
      "rowkye3", filterList)

    rsopt.foreach(resultScanner => {
      resultScanner.foreach(result => {
        println("rowkey = " + Bytes.toString(result.getRow))
        println("fileName = " + Bytes.toString(result.getValue("fileInfo", "name")))
      })
      resultScanner.close()
    })
  }


  @Test
  def keyOnlyFilterTest(): Unit = {

    import scala.collection.JavaConversions._

    val filter = new KeyOnlyFilter(true)

    val filterList = new FilterList(Operator.MUST_PASS_ALL, filter :: Nil)

    val rsopt: Option[ResultScanner] = HBaseHelper.resultScannerOption(tableName,
      "rowkey1",
      "rowkye3", filterList)

    rsopt.foreach(resultScanner => {
      resultScanner.foreach(result => {
        println("rowkey = " + Bytes.toString(result.getRow))
        println("fileName = " + Bytes.toString(result.getValue(
          Bytes.toBytes("fileInfo"), Bytes.toBytes("name")
        )))
      })
      resultScanner.close()
    })
  }

  @Test
  def columnPrefixFilterTest() : Unit = {
    val filter = new ColumnPrefixFilter("name")
    //val filterList = new FilterList(Operator.MUST_PASS_ALL, filter :: Nil)

    val opts = HBaseHelper.resultScannerOption(tableName,
      "rowkey1", "rowkey3", filter)

    opts.foreach(HBaseHelper.withResultScanner(_) {
      _.foreach(result => {
        println("rowke ==> "+ Bytes.toString(result.getRow))
        println("fileName ==> "+ Bytes.toString(result.getValue(
          "fileInfo", "name"
        )))
        println("fileType ==> "+ Bytes.toString(result.getValue(
          "fileInfo", "type"
        )))
      })
    })

  }
}
