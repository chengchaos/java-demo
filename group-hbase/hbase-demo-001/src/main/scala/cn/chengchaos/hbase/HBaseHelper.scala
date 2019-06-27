package cn.chengchaos.hbase

import com.typesafe.config.{Config, ConfigFactory}
import grizzled.slf4j.Logger
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Delete, Get, HBaseAdmin, Put, Result, ResultScanner, Scan, Table}
import org.apache.hadoop.hbase.filter.{Filter, FilterList}
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

  val config : Config = ConfigFactory.load()

  val zookeeperQuorum: String = config.getString("hbase.zookeeper.quorum")
  val rootdir: String = config.getString("hbase.rootdir")

  private lazy val configuration: Configuration = createHadoopConfiguration()

  @volatile
  private var connection: Connection = _

  def nsar[A](whatever: A)(setBlock: A => Any): A = {
    setBlock(whatever)
    whatever
  }

  implicit def string2bytes(input: String) : Array[Byte] = Bytes.toBytes(input)

  def createHadoopConfiguration(): Configuration = {
    this.nsar(HBaseConfiguration.create()) {
      conf => {
        logger.info(s"quorum ==> $zookeeperQuorum, rootdir ==> $rootdir")

        conf.set("hbase.zookeeper.quorum", zookeeperQuorum)
        conf.set("hbase.rootdir", rootdir)
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
        val res = block(admin)
        res match  {
          case b: Boolean => b
          case  _ => true
        }
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

  def deleteTable(tabName: String): Boolean = {
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
  def put1Cell(tabName: String, rowKey: String, cfName: String, qualifier: String, data: String): Option[Boolean] = {

    this.withTable(tabName) {
      table => {
        val put: Put = new Put(rowKey)
        put.addColumn(cfName, qualifier, data)
        table.put(put)
        Option(true)
      }
    }

  }

  def createCell(rowKey: String, cfName: String, qualifier: String, data: String) : Put = {
    this.nsar(new Put(rowKey)) {
      put => put.addColumn(cfName, qualifier, data)
    }
  }

  def putCells(tabName: String, puts: Seq[Put]): Unit = {

    import scala.collection.JavaConversions._

    this.withTable(tabName) {
      table => {
        table.put(puts)
        None
      }
    }

  }


  def resultOption(tabName: String, rowKey: String, filter: Filter = null): Option[Result] = {
    this.withTable(tabName) {
      table => {
        val get = new Get(rowKey)
        if (filter != null) {
          get.setFilter(filter)
        }
        Option(table.get(get))
      }
    }
  }


  def resultScannerOption(tabName: String): Option[ResultScanner] = {
    resultScannerOption(tabName, 1000)
  }

  def resultScannerOption(tabName: String, caching: Int): Option[ResultScanner] = {
    this.withTable(tabName) {
      table => {
        val scan = new Scan()
        scan.setCaching(caching)
        Option(table.getScanner(scan))
      }
    }
  }



  def resultScannerOption(tabName: String,
                          startRowKey: String,
                          stopRowKey: String,
                          filter: Filter = null,
                          caching: Int = 1000 ): Option[ResultScanner] = {
    this.withTable(tabName) {
      table => {

        val scan = new Scan()
        scan.setStartRow(startRowKey)
        scan.setStopRow(stopRowKey)
        if (filter != null) {
          scan.setFilter(filter)
        }
        scan.setCaching(caching)

        Option(table.getScanner(scan))
      }
    }
  }

  def resultScannerOption(tabName: String, startRowKey: String, stopRowKey: String, caching: Int): Option[ResultScanner] = {
    this.withTable(tabName) {
      table => {

        val scan = new Scan()
        scan.setStartRow(startRowKey)
        scan.setStopRow(stopRowKey)
        scan.setCaching(caching)

        Option(table.getScanner(scan))
      }
    }
  }

  def deleteRow(tabName: String, rowKey: String): Option[Boolean] = {
    this.withTable(tabName) {
      table => {
        table.delete(new Delete(rowKey))
        Option(true)
      }
    }
  }

  def deleteQualifier(tabName: String, rowKey: String, cfName: String, qualifier: String): Option[Boolean] = {
    this.withTable(tabName) {
      table => {
        val delete = new Delete(rowKey)
        delete.addColumn(cfName, qualifier)
        table.delete(delete)
        Option(true)
      }
    }
  }




  def withResultScanner[A](resultScanner: ResultScanner)(callback: ResultScanner => A) : A = {

    try {
      callback(resultScanner)
    } finally {
      resultScanner.close()
    }
  }

}
