package cn.chengchaos.hbase.hos

import java.nio.ByteBuffer

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
class HosStroe {

  def createBucketStore(): Unit = ???

  def deleteBucketStore(): Unit = ???

  def createSeqTable(): Unit = ???

  def put(bucket: String
          , key: String
          , content: ByteBuffer
          , length: Long
          , metaType: String
          , propertyes: Map[String, String]): Unit = ???

  def summary(bucket: String, key: String): HosObjectSummary = ???

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
