package cn.chengchaos.hbase.hos

import java.nio.ByteBuffer

import com.google.common.base.Strings
import org.apache.curator.framework.recipes.locks.{InterProcessMultiLock, InterProcessMutex}
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.hadoop.hbase.client.{Connection, Put, Result}
import org.apache.hadoop.hbase.io.ByteBufferInputStream
import org.apache.hadoop.hbase.util.Bytes

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/26 19:05 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class HosStroe(val connection: Connection
               , hdfsService: HdfsService
               , zkUrls: String) {

  private val zkClient: CuratorFramework = CuratorFrameworkFactory
    .newClient(zkUrls
      , new ExponentialBackoffRetry(20, 5))

  implicit def string2bytes(in: String): Array[Byte] = Bytes.toBytes(in)

  implicit def long2bytes(in: Long): Array[Byte] = Bytes.toBytes(in)

  implicit def integer2bytes(in: Int): Array[Byte] = Bytes.toBytes(in)

  import scala.collection.JavaConversions._
  import scala.compat.java8.FunctionConverters._

  /**
    *
    * @param bucket
    */
  def createBucketStore(bucket: String): Unit = {

    //  , HosConfig.getDirColumnFamily())

    // 2: 创建文件表
    HBaseService.createTable(HosConfig.getObjectTableName(bucket)
      , HosConfig.getObjColumnFamily()
      , HosConfig.OBJ_REGIONS)

    // 3: 添加到 seq 表
    val put: Put = new Put("bucket")
    put.addColumn(HosConfig.BUCKET_DIR_SEQ_CF_BYTES
      , HosConfig.BUCKET_DIR_SEQ_QUALIFIER
      , 0L)
    HBaseService.putRow(HosConfig.BUCKET_DIR_SEQ_TABLE, put)


    // 4: 创建 hdfs 目录
    hdfsService.mkdir(HosConfig.FILE_STORE_ROOT + "/" + bucket)
  }

  /**
    *
    * @param bucket
    */
  def deleteBucketStore(bucket: String): Unit = {

    // 删除目录表和文件表
    HBaseService.deleteTable(HosConfig.getObjectTableName(bucket))
    HBaseService.deleteTable(HosConfig.getDirTableName(bucket))

    // 删除 seq 中的表记录
    HBaseService.deleteRow(HosConfig.BUCKET_DIR_SEQ_TABLE, "bucket")

    // 删除 hdfs 上的目录
    hdfsService.rmdir(HosConfig.FILE_STORE_ROOT + "/" + bucket)

  }

  /**
    *
    */
  def createSeqTable(): Unit = {

    HBaseService.createTable(HosConfig.BUCKET_DIR_SEQ_TABLE
      , Array(HosConfig.BUCKET_DIR_SEQ_CF)
    )
  }

  def put(bucket: String
          , key: String
          , content: ByteBuffer
          , length: Long
          , mediaType: String
          , propertyes: Map[String, String]): Unit = {
    // 判断是否是创建目录， 是否上传文件
    // key 以斜杠 `/` 结尾的，视为创建目录

    if (key.endsWith("/")) {
      putDir(bucket, key)
    }

    else {
      // 获取 seq id
      val dir = key.substring(0, key.lastIndexOf("/") + 1)
      var hash: String = null

      while (hash == null) {
        if (dirNotExist(bucket, dir)) {
          hash = putDir(bucket, dir)
        } else {
          hash = getDirSeqId(bucket, dir)
        }
      }
      /* 上传文件到文件表 */

      // 获取锁
      val lockKey = key.replaceAll("/", "_")
      val lock = new InterProcessMutex(zkClient, lockKey)
      try {
        lock.acquire()
        val fileKey = hash + "_"+ key.substring(key.lastIndexOf("/" ) + 1)
        val contentPut = new Put(fileKey)

        if (!Strings.isNullOrEmpty(mediaType)) {
          contentPut.addColumn(
            HosConfig.OBJ_META_CF
            , HosConfig.OBJ_MEDIATYPE_QUALIFIER
            , mediaType

          )
        }
        // TODO： 还有 props， length 等等

        if (length <= HosConfig.FILE_STORE_THRESHOLD) {
          // 小于 20M 存储到 HBase
          val byteBuffer = ByteBuffer.wrap(HosConfig.OBJ_CONT_QUALIFIER)
          contentPut.addColumn(HosConfig.OBJ_CONT_CF_BYTES, byteBuffer
          , System.currentTimeMillis(), content)
        } else {
          /// 大于 20M 存储到 HDFS
          val fileDir = HosConfig.FILE_STORE_ROOT +
           "/" + bucket
           "/" + hash

          val name = key.substring(key.lastIndexOf("/") + 1)
          val inputStream = new ByteBufferInputStream(content)
          hdfsService.saveFile(fileDir
            , name
            , inputStream
            , length
            , 1
          )

        }
        HBaseService.putRow(HosConfig.getObjectTableName(bucket)
          , contentPut)
      } finally {
        if (lock != null) lock.release()
      }

      // 上传文件

      // 释放锁


    }
  }

  def dirExist(bucket: String, rowKey: String): Boolean = {
    HBaseService.existsRow(HosConfig.getDirTableName(bucket), rowKey)
  }

  def getDirSeqId(bucket: String, rowKey: String): String = {

    val either = HBaseService.readLine(HosConfig.getDirTableName(bucket), rowKey)
    either match {
      case Right(result) =>
        Bytes.toString(result.getValue(
          HosConfig.DIR_META_CF_BYTES
          , HosConfig.DIR_SEQID_QUALIFIER
        ))
      case _ => null
    }

  }

  def putDir(bucket: String, key: String): String = {

    if (dirNotExist(bucket, key)) {
      // 从 zk 获取锁
      // 创建目录
      // 释放锁
      val locKey: String = key.replaceAll("/", "_")
      val lock = new InterProcessMutex(zkClient, "/host" + bucket + "/" + locKey)
      try {
        lock.acquire()
        val dir1 = key.substring(0, key.lastIndexOf("/"))
        val name = dir1.substring(dir1.lastIndexOf("/"))
        if (name.length > 0) {
          val parent = dir1.substring(0, dir1.lastIndexOf("/"))
          if (dirNotExist(bucket, parent)) {
            this.putDir(bucket, parent)
          }
          // 在父目录添加 sub 列族内 添加子项
          val put = new Put(parent)
          put.addColumn(HosConfig.DIR_SUBDIR_CF_BYTES, name, 1)
          HBaseService.putRow(HosConfig.getDirTableName(bucket), put)
        }
        // 再添加到目录表
        val seqId = getDirSeqId(bucket, key)
        val hash: String = if (seqId == null) {
          makeDirSeqId(bucket)
        } else {
          seqId
        }

        val dirPut = new Put(key)
        dirPut.addColumn(HosConfig.DIR_META_CF_BYTES
          , HosConfig.DIR_SEQID_QUALIFIER
          , hash)
        HBaseService.putRow(HosConfig.getDirTableName(bucket), dirPut)

        hash
      } finally {
        if (lock != null) {
          lock.release()
        }
      }
    } else {
      null
    }
  }

  def makeDirSeqId(bucket: String) : String = {
    val option = HBaseService.incrementColumnValue(
      HosConfig.BUCKET_DIR_SEQ_TABLE
      , bucket
      , HosConfig.BUCKET_DIR_SEQ_CF
      , HosConfig.BUCKET_DIR_SEQ_QUALIFIER
      , 1
    )

    if (option.isDefined) {
      val v = option.get
      String.format("%da%d", v % 64, v)
    } else {
      throw new IllegalStateException("什么情况会获取不到 increment 值？")
    }

  }


  def dirNotExist(bucket: String, key: String): Boolean = !dirExist(bucket, key)


  def summary(bucket: String, key: String): HosObjectSummary = {
    // 如果是文件夹

    // 如矤文件
  }

  def summaries(bucket: String, startKey: String, stopKey: String): List[HosObjectSummary] = ???

  def listDir(bucket: String
              , dir: String
              , start: String
              , maxCount: Int): ObjectListResult = ???


  def listDirByProfix(bucket: String
                      , dir: String
                      , prefix: String
                      , start: String
                      , maxCount: Int): ObjectListResult = ???


  def hosObject(bucket: String, key: String): HosObject = ???


  def deleteHosObject(bucket: String, key: String): Unit = ???


}
