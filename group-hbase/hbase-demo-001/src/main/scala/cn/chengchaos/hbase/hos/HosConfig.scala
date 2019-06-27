package cn.chengchaos.hbase.hos

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.hadoop.hbase.util.Bytes

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/26 11:33 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object HosConfig {

  val DIR_TABLE_PREFIX: String  = "hos_dir_"
  val OBJ_TABLE_PREFIX: String = "hos_obj_"

  val DIR_META_CF: String = "cf"
  val DIR_SUBDIR_CF: String = "sub"


  val DIR_META_CF_BYTES: Array[Byte]  = Bytes.toBytes(DIR_META_CF)
  val DIR_SUBDIR_CF_BYTES: Array[Byte] = Bytes.toBytes(DIR_SUBDIR_CF)

  val OBJ_META_CF: String = "cf"
  val OBJ_SUBDIR_CF: String = "c"


  val OBJ_META_CF_BYTES: Array[Byte] = Bytes.toBytes(OBJ_META_CF)
  val OBJ_SUBDIR_CF_BYTES: Array[Byte] = Bytes.toBytes(OBJ_SUBDIR_CF)

  val DIR_SEQID_QUALIFIER: Array[Byte] = Bytes.toBytes("u")
  val OBJ_CONT_QUALIFIER: Array[Byte] = Bytes.toBytes("c")
  val OBJ_LEN_QUALIFIER: Array[Byte] = Bytes.toBytes("l")
  val OBJ_PROPS_QUALIFIER: Array[Byte] = Bytes.toBytes("p")
  val OBJ_MEDIATYPE_QUALIFIER: Array[Byte] = Bytes.toBytes("m")

  val FILE_STORE_ROOT: String = "/hos";

  val fILE_STORE_THRESHOLD: Int = 20 * 1024 * 1024

  val BUCKET_DIR_SEQ_TABLE :String = "hos_dir_seq"

  val BUCKET_DIR_SEQ_CF: String = "s"

  val BUCKET_DIR_SEQ_CF_BYTES: Array[Byte] = Bytes.toBytes(BUCKET_DIR_SEQ_CF)

  val BUCKET_DIR_SEQ_QUALIFIER: Array[Byte] = Bytes.toBytes("s")

  def getDirTableName(bucketName: String) : String = {
    DIR_TABLE_PREFIX + bucketName
  }

  def getObjectTableName(bucketName: String) : String = {
    OBJ_TABLE_PREFIX + bucketName
  }

  def getDirColumnFamily() : Array[String] = {
    Array(DIR_META_CF, DIR_SUBDIR_CF)
  }


  def getObjColumnFamily() : Array[String] = {
    Array(OBJ_META_CF, OBJ_SUBDIR_CF)
  }


  private val config: Config = ConfigFactory.load

  def getString(path: String): String = config.getString(path)
}
