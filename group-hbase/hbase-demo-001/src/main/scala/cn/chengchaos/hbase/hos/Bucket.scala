package cn.chengchaos.hbase.hos

import java.util.{Date, UUID}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/26 11:01 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class Bucket {

  var bucketId: String = _
  var bucketName: String = _
  var creator: String = _
  var detail: String = _
  var createTime: Date = _


}

object Bucket {

  def apply(bucketId: String, bucketName: String, creator: String, detail: String, createTime: Date): Bucket = {

    val bucket = new Bucket
    bucket.bucketId = bucketId
    bucket.bucketName = bucketName
    bucket.creator = creator
    bucket.detail = detail
    bucket.createTime = createTime

    bucket
  }

  def apply(bucketName: String, creator: String, detail: String): Bucket = {

    var bucketId = UUID.randomUUID().toString
    var createTime = new Date()

    apply(bucketId, bucketName, creator, detail, createTime)

  }
}
