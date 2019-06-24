package cn.chengchaos.hbase

import grizzled.slf4j.Logger
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Delete, Get, HBaseAdmin, Put, Result, ResultScanner, Scan, Table}
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.hadoop.hbase.util.Bytes

/**
  * <p>
  * <strong>
  *   操作 HBase 的 Scala 工具类
  * </strong><br /><br />
  * 如题
  * </p>
  *
  * @author chengchao - 2019-06-22 18:53 <br />
  * @since [产品模块版本]
  *
  */
object HBaseHelper {

  val logger: Logger = Logger[HBaseHelper.type]

  private lazy val configuration: Configuration = createHadoopConfiguration()

  @volatile
  private var connection: Connection = _

  def nsar[A](whatever: A)(setBlock: A => Any): A = {
    setBlock(whatever)
    whatever
  }

  def createHadoopConfiguration(): Configuration = {
    this.nsar(HBaseConfiguration.create()) {
      conf => {
        conf.set("hbase.zookeeper.quorum", "t420i:2181")
        conf.set("hbase.rootdir", "hdfs://t420i:8080/hbase")
      }
    }
  }

  def getConnection: Connection = {

    if (this.connection == null) {
      connection = ConnectionFactory.createConnection(configuration)
    }
    connection
  }

  def closeConnection(): Unit = {
    if (this.connection != null && !connection.isClosed) {

      if (logger.isInfoEnabled) {
        val isClosed = connection.isClosed
        logger.info(s"connection ==> $connection and is closed ==> $isClosed")
      }
      connection.close()
    }
  }

  def getTable(tableName: String): Option[Table ]= {
    val table = this.getConnection.getTable(TableName.valueOf(tableName))
    if (table != null) Option(table)
    else None
  }

  /**
    *
    * @param block 回调
    * @return
    */
  def withHBaseAdmin(block: HBaseAdmin => Any): Boolean = {

    val admin: HBaseAdmin = getConnection.getAdmin.asInstanceOf[HBaseAdmin]
    logger.info(s"admin ==> $admin")

    if (admin != null) {
      try {
        block(admin)
        true
      } catch {
        case e: Exception =>
          logger.error("ex", e)
          false
      } finally {
        admin.close()
      }
    } else {
      false
    }

  }


  /**
    *
    * @param tabName 表名称
    * @param block   回调
    * @return
    */
  def withTable[A](tabName: String)(block: Table => Option[A]): Option[A] = {

    val tableOption = getTable(tabName)

    if (tableOption.nonEmpty) {
      val table = tableOption.get
      try {
        block(table)
      } catch {
        case e: Exception => logger.error("ex", e)
          None
      } finally {
        table.close()
      }
    }
    else {
      None
    }
  }

  /**
    *
    * @param tabName 表名
    * @param columnFamilies     列族
    * @return
    */
  def createTable(tabName: String, columnFamilies: Array[String]): Boolean = {

    this.withHBaseAdmin(admin => {

      val tableName = TableName.valueOf(tabName)
      val tableDescriptor: HTableDescriptor = new HTableDescriptor(tableName)
      logger.info(s"tableName ==> $tableName")
      columnFamilies.foreach(cf => {
        val columnDescriptor = new HColumnDescriptor(cf)
        columnDescriptor.setMaxVersions(1)
        tableDescriptor.addFamily(columnDescriptor)
      })
      logger.info(s"table descriptor ==> $tableDescriptor")
      admin.createTable(tableDescriptor)
      logger.info("create success")
    })

  }

  def deleteTAble(tabName: String): Boolean = {
    this.withHBaseAdmin(admin => {
      admin.disableTable(tabName)
      admin.deleteTable(tabName)
    })
  }

  /**
    *
    * @param tabName 表名称
    * @param cfName  列族名
    * @return
    */
  def deleteColumnFamily(tabName: String, cfName: String): Boolean = {
    this.withHBaseAdmin(_.deleteColumn(tabName, cfName))
  }

  /**
    *
    * @param tabName   表名称
    * @param rowKey    Row key
    * @param cfName    列族
    * @param qualifier 字段
    * @param data      字段值
    * @return
    */
  def putRow(tabName: String, rowKey: String, cfName: String, qualifier: String, data: String): Option[Boolean] = {

    this.withTable(tabName) {
      table => {
        val put: Put = new Put(Bytes.toBytes(rowKey))
        put.addColumn(Bytes.toBytes(cfName),
          Bytes.toBytes(qualifier),
          Bytes.toBytes(data))

        table.put(put)
        Option(true)
      }
    }

  }

  def pubRows(tabName: String, puts: Seq[Put]): Unit = {

    import scala.collection.JavaConversions._

    this.withTable(tabName) {
      table => {
        table.put(puts)
        None
      }
    }

  }


  def getRow(tabName: String, rowKey: String, filterList: FilterList = null): Option[Result] = {
    this.withTable(tabName) {
      table => {
        val get = new Get(Bytes.toBytes(rowKey))
        if (filterList != null) {
          get.setFilter(filterList)
        }
        Option(table.get(get))
      }
    }
  }

  def getScanner(tabName: String, caching: Int = 1000): Option[ResultScanner] = {
    this.withTable(tabName) {
      table => {
        val scan = new Scan()
        scan.setCaching(caching)
        Option(table.getScanner(scan))
      }
    }
  }


  def getScanner(tabName: String, startRowKey: String, stopRowKey: String): Option[ResultScanner] = {
    this.getScanner(tabName, startRowKey, stopRowKey, 1000)
  }

  def getScanner(tabName: String, startRowKey: String, stopRowKey: String, caching: Int): Option[ResultScanner] = {
    this.withTable(tabName) {
      table => {

        val scan = new Scan()
        scan.setStartRow(Bytes.toBytes(startRowKey))
        scan.setStopRow(Bytes.toBytes(stopRowKey))
        scan.setCaching(caching)

        Option(table.getScanner(scan))
      }
    }
  }

  def deleteRow(tabName: String, rowKey: String): Option[Boolean] = {
    this.withTable(tabName) {
      table => {
        table.delete(new Delete(Bytes.toBytes(rowKey)))
        Option(true)
      }
    }
  }

  def deleteQualifier(tabName: String, rowKey: String, cfName: String, qualifier: String): Option[Boolean] = {
    this.withTable(tabName) {
      table => {
        val delete = new Delete(Bytes.toBytes(rowKey))
        delete.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(qualifier))
        table.delete(delete)
        Option(true)
      }
    }
  }

}
