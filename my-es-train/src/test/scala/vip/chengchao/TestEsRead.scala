package vip.chengchao

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.http.HttpHost
import org.elasticsearch.ElasticsearchException
import org.elasticsearch.action.get.{GetRequest, GetResponse}
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.client.{RequestOptions, RestClient, RestHighLevelClient}
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.fetch.subphase.FetchSourceContext

import scala.annotation.tailrec
import scala.collection.JavaConversions

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/28 0028 上午 9:24 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object TestEsRead extends LazyLogging {

  /**
    * The high-level client will internally create the low-level client used to perform
    * requests based on the provided builder. That low-level client maintains a pool of
    * connections and starts some threads so you should close the high-level client when
    * you are well and truly done with it and it will in turn close the internal low-level
    * client to free those resources. This can be done through the close:
    * {{{close}}}
    *
    * @return
    */
  def withClient[T](fn: RestHighLevelClient => T): Option[T] = {

    val httpHost = new HttpHost("192.168.3.14", 9200, "http")
    val client = new RestHighLevelClient(RestClient.builder(httpHost))

    try {
      Option(fn(client))
    } catch {
      case e: ElasticsearchException =>
        logger.error("", e)
        if (RestStatus.CONFLICT.equals(e.status())) {
          println("CONFLICT")
        }
        None
    } finally {
      client.close()
    }

  }


  def createIndexMethod0(request: IndexRequest): Unit = {

    val indexResponseRepo: Option[IndexResponse] =
      withClient(client => client.index(request, RequestOptions.DEFAULT))

    if (indexResponseRepo.nonEmpty) {
      val indexResponse = indexResponseRepo.get
      import org.elasticsearch.action.DocWriteResponse
      val index = indexResponse.getIndex
      val id = indexResponse.getId
      if (indexResponse.getResult eq DocWriteResponse.Result.CREATED) {
        logger.info("Handle (if needed) the case where the document was created for the first time")
      }
      else if (indexResponse.getResult eq DocWriteResponse.Result.UPDATED) {
        logger.info("Handle (if needed) the case where the document was rewritten as it was already existing")
      }
      val shardInfo = indexResponse.getShardInfo
      if (shardInfo.getTotal != shardInfo.getSuccessful) {
        logger.info("Handle the situation where number of successful shards is less than total shards")
      }
      if (shardInfo.getFailed > 0) {
        logger.info("Handle the potential failures")
        for (failure <- shardInfo.getFailures) {
          val reason = failure.reason
          logger.info("reason: " + reason)
        }
      }
    } else {
      logger.info("indexResponseRepo is Empty")
    }
  }


  /**
    *
    * 参考链接:
    * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.1/java-rest-high-document-index.html
    *
    * @return
    */
  def createIndexRequest01(): IndexRequest = {

    val json =
      """
        |{
        |"user_name" : "kimchy",
        |"postDate":"2013-01-30",
        |"message":"trying out Elasticsearch"
        |}
      """.stripMargin

    val index = "posts"
    new IndexRequest(index)
      .id("1")
      .source(json, XContentType.JSON)
  }

  def createIndexRequest02(): IndexRequest = {

    val jsonMap = Map(
      "user" -> "kimchy",
      "postDate" -> new Date(),
      "message" -> "trying out Elasticsearch"
    )
    import scala.collection.JavaConversions._

    val javaMap: java.util.Map[String, Object] = JavaConversions.mapAsJavaMap(jsonMap)


    new IndexRequest("posts2")
      .id("i")
      .source(jsonMap)

  }

  def createIndexRequest03(): IndexRequest = {

    val builder: XContentBuilder = XContentFactory.jsonBuilder()

    builder.startObject()
      .field("user", "kimchy")
      .timeField("postDate", new Date())
      .field("message", "trying out Es")
      .endObject()

    new IndexRequest("posts3")
      .id("1")
      .source(builder)

  }

  def createGetRequest01(): GetRequest = {

    val index = "posts"
    val documentId = "1"
    new GetRequest(index, documentId)
      // Disable fetching _source.
      .fetchSourceContext(new FetchSourceContext(false))
      // Disable fetching stored fields.
      .storedFields("_none_")
  }

  def executeGet(request: GetRequest): Unit = {

    val request = createGetRequest01()
    val responseOpt = withClient(client => {
      client.get(request, RequestOptions.DEFAULT)
    })

    responseOpt match {
      case Some(response) =>

        if (response.isExists) {
          val version = response.getVersion
          logger.info("version ==> {}", version.asInstanceOf[AnyRef])

          val sourceAsString = response.getSourceAsString
          logger.info("sourceAsString ==> {}", sourceAsString)

          val source = response.getSource
          //val sourceAsMap = JavaConversions.mapAsScalaMap(response.getSourceAsMap)
          //logger.info("sourceAsMap == sourceMap ==> {}", (sourceAsMap == sourceMap).asInstanceOf[AnyRef])
          //        .getValue.toString

          logger.info("===============")
          if (source != null && !source.isEmpty) {
            val sourceMap = JavaConversions.mapAsScalaMap(source)
            sourceMap.foreach(ent => {
              println("key = " + ent._1 + ", value = " + ent._2)
            })
            logger.info("message ==> {}", sourceMap("message"))
          }
          logger.info("===============")

          /*
           * Retrieve the `message` stored field
           *  (requires the field to be stored separately in the mappings)
           */
          val documentField = response.getField("message")

          if (documentField != null) {
            val v = documentField.getValue
            println(v.getClass.getName)
          }


          logger.info("response ==> {}", response.asInstanceOf[AnyRef])
          logger.info("documentField ==> {}", documentField.asInstanceOf[AnyRef])
        }

      case None => println("None")
    }

  }


  def main(args: Array[String]): Unit = {
//    executeGet(createGetRequest01())

    val format = DateTimeFormatter.ofPattern("yyyyMMddhhmmss")


    val index = "my-vehicle"
    val end = 100
    val begin = System.currentTimeMillis()

    withClient(client => {

      def save(time : String, base : Int): Unit = {
        for (suffix <- 1000 to 1599) {
          val vin = "ABCDEFABCDEF3"+suffix
          val id = vin + time
//          println("exec count ==> "+ (suffix * base))
          val builder: XContentBuilder = XContentFactory.jsonBuilder()
            .startObject()
            .field("vin", vin)
            .timeField("post_date", time)
            .field("message", vin + " "+ time)
            .endObject()

          val indexRequest = new IndexRequest(index)
            .id(id)
            .source(builder)
          client.index(indexRequest, RequestOptions.DEFAULT)
        }
      }

      @tailrec
      def exec(begin: Int, current: LocalDateTime) : Unit = {
        val time = current.format(format)
        println("exec count ==> "+ (begin))
        save(time, begin)
        if (begin <= end) {
//          TimeUnit.MILLISECONDS.sleep(50L)
          exec(begin + 1, current.plusSeconds(10L))
        }
      }

      val localDateTime = LocalDateTime.of(
        2018, 7, 20,
        9, 30, 0)
      exec(1, localDateTime)

    })


    println("耗时: "+ (System.currentTimeMillis() - begin) / 1000.0D)

  }
}
