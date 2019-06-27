package cn.chengchaos.hbase.hos

import java.io.InputStream
import java.net.URI

import grizzled.slf4j.Logger
import org.apache.commons.io.FileExistsException
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.permission.FsPermission
import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, Path}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/26 16:54 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class HdfsService {

  private val logger: Logger = Logger(classOf[HdfsService])

  /**
    * 128 M
    */
  private val defaultBlockSize: Long = 128 * 1024 * 1024;

  /**
    * 64 M
    */
  private val initBlockSize: Long = defaultBlockSize / 2;


  // 1: 读取 hdfs 先关的配置信息

  val confDir = HosConfig.getString("hadoop.conf.dir")
  // hdfs://localhost:8020
  val hdfsUrl = HosConfig.getString("hadoop.uri")

  // 2: 通过配置获取一个  FileSystem 的实例
  val configuration: Configuration = new Configuration
  configuration.addResource(new Path(confDir + "/hdfs-site.xml" ))
  configuration.addResource(new Path(confDir + "/core-site.xml" ))

  val fileSystem: FileSystem = FileSystem.get(new URI(hdfsUrl), configuration)

  implicit def string2Path(input: String) : Path = new Path(input)

  def saveFile(dir: String
               , name: String
               , inputStream: InputStream
               , length: Int
               , relication: Short) : Unit = {
    // 1 : 判断 dir 是否存在， 不存在则新建

    val dirPath = new Path(dir)
    try {
      if (!fileSystem.exists(dirPath)) {
        val succ = fileSystem.mkdirs(dirPath, FsPermission.getDefault)
        logger.info(s"create dir ==> $dirPath")
        if (!succ) throw new RuntimeException("创建 dir 失败")
      }
    } catch {
      case  e: FileExistsException => logger.error("emmmmm", e)
    }

    // 2: 保存文件
    val path = new Path(dir + "/"+ name)
    val blockSie = if (length <= initBlockSize) length
    else defaultBlockSize

    val outputStream : FSDataOutputStream = fileSystem.create(
      path
      , true
      , 512 * 1024
      , relication
      , blockSie
      )

    try {
      fileSystem.setPermission(path, FsPermission.getFileDefault)
      val buffer: Array[Byte] = new Array(512 * 1024)
      var len = inputStream.read(buffer)
      while (len > 0) {
        outputStream.write(buffer, 0, len)
        len = inputStream.read(buffer)
      }
    } finally {
      inputStream.close()
      outputStream.close()
    }

  }

  /**
    *
    * @param dir
    * @param name
    */
  def deleteFile(dir: String
                , name: String) : Unit = {
    fileSystem.delete(dir + "/"+ name, false)
  }

  /**
    *
    * @param dir
    * @param name
    * @return
    */
  def openFile(dir: String, name: String) : InputStream =
    fileSystem.open(dir + "/"+ name)


  def mkdir(dir: String) : Unit = fileSystem.mkdirs(dir)

  def rmdir(dir: String) : Unit = fileSystem.delete(dir, true)

}
