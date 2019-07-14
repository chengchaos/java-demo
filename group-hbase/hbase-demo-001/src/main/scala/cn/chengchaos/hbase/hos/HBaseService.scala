package cn.chengchaos.hbase.hos

import java.util

import cn.chengchaos.hbase.HBaseHelper
import org.apache.hadoop.hbase.client.{Get, Put, Result}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor, TableName}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/26 18:01 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object HBaseService {

  import scala.collection.JavaConversions._
  import scala.compat.java8.FunctionConverters._
  implicit def string2bytes(in: String): Array[Byte] = Bytes.toBytes(in)

  implicit def long2bytes(in: Long): Array[Byte] = Bytes.toBytes(in)

  implicit def integer2bytes(in: Int): Array[Byte] = Bytes.toBytes(in)
  /**
    * 创建表
    *
    * @param tableName
    * @param cfs
    * @param splitKeys 预先分区
    * @return
    */
  def createTable(tableName: String, cfs: Array[String],
                  splitKeys: Array[Array[Byte]] = null): Boolean = {
    HBaseHelper.withHBaseAdmin(admin => {
      if (admin.tableExists(tableName)) false
      else {
        val tableDescriptor: HTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName))
        cfs.foreach(cf => {
          val colDescriptor: HColumnDescriptor = new HColumnDescriptor(cf)
          colDescriptor.setMaxVersions(1)
          tableDescriptor.addFamily(colDescriptor)
        })
        admin.createTable(tableDescriptor, splitKeys)
        true
      }

    })
  }

  /**
    * 2 删除表
    *
    * @param tableName
    * @return
    */
  def deleteTable(tableName: String): Boolean = {
    HBaseHelper.withHBaseAdmin(admin => {
      admin.deleteTable(tableName)
    })
    //    HBaseHelper.deleteTable(tableName)
  }

  /**
    * 3 删除列族
    *
    * @param tableName
    * @param cfName
    * @return
    */

  def deleteColumnFamily(tableName: String, cfName: String): Boolean = {
    HBaseHelper.deleteColumnFamily(tableName, cfName)
  }

  /**
    * 4 删除列S
    *
    * @param tableName
    * @param rowKey
    * @param cf
    * @param qualifier
    * @return
    */
  def celeteColumnQualifier(tableName: String
                            , rowKey: String
                            , cf: String
                            , qualifier: String): Boolean = {
    HBaseHelper.deleteQualifier(tableName, rowKey, cf, qualifier)
      .getOrElse(false)
  }

  /**
    * 5 删除行
    *
    * @param tableName
    * @param rowKey
    * @return
    */
  def deleteRow(tableName: String, rowKey: String): Boolean = {
    HBaseHelper.deleteRow(tableName, rowKey).getOrElse(false)
  }

  /**
    * 6 读取行
    *
    * @param tableName
    * @param rowKey
    * @return
    */
  def readLine(tableName: String, rowKey: String): Either[Boolean, Result] = {
    val option = HBaseHelper.resultOption(tableName, rowKey)
    if (option.isDefined) {
      Right(option.get)
    }
    else {
      Left(false)
    }
  }

  // 7 读取 Scanner

  // 8 插入行
  def putRow(tableName: String, put: Put): Unit = {
    HBaseHelper.putCells(tableName, put :: Nil)
  }

  // 9 批量插入

  // 10 incrementColumnValue
  def incrementColumnValue(tableName: String
                           , rowkey: String
                           , cf: Array[Byte]
                           , qualifier: Array[Byte]
                           , num: Int): Option[Long] = {

    HBaseHelper.withTable(tableName) {
      table => {
        val longResult = table.incrementColumnValue(rowkey
        , cf
        , qualifier
        , num)
        Some(longResult)
      }
    }
  }

  def existsRow(tableName: String, rowKey: String) : Boolean = {
    HBaseHelper.withTable(tableName) {
      table => {
        Some(table.exists(new Get(rowKey)))
      }
    }.getOrElse(false)
  }
}
