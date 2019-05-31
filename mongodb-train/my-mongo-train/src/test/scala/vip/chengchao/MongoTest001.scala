package vip.chengchao

import java.util.concurrent.{CompletableFuture, TimeUnit}
import java.util.function.Consumer

import org.junit.Test
import org.mongodb.scala.{Completed, FindObservable, MongoClient, Observer, SingleObservable}
import org.mongodb.scala.bson.collection.mutable.Document

import scala.concurrent.Await
import scala.concurrent.duration.Duration
//import org.mongodb.scala.bson.Document

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/30 0030 下午 5:54 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class MongoTest001 {


  import Helpers._


  @Test def testSave: Unit = {
    val mongo = MongoClient("mongodb://127.0.0.1:27017")

    val database = mongo.getDatabase("chengchao")


    println("mongo database == " + database)

    val collectionName = "sampleCollection"
    //    database.createCollection("sampleCollection")

    val collection = database.getCollection(collectionName)

    println("collection ==> " + collection)

    val document = Document() += (
      ("title", "MongoDB"),
      ("id", 2),
      ("description", "database"),
      ("likes", 100),
      ("url", "http://www.chengchaos.cn"),
      ("by", "tutorials point")
    )
    val bson = document.toBsonDocument
    println("document ==> " + document)
    println("bson ==> " + bson)


    val seq = collection.insertOne(bson).results()

    seq.foreach(c => println("c ==> " + c))

    println(" ================ ")


  }


  @Test def testGet: Unit = {

    val mongo = MongoClient("mongodb://localhost:27017")
    val database = mongo.getDatabase("chengchao")

    println("Collection created successfully")
    import scala.collection.JavaConversions._
    for (name <- database.listCollectionNames) {
      println(name)
    }
    println("mongo database == " + database)

    val collectionName = "sampleCollection"
    val collection = database.getCollection(collectionName)


    val findObservable: FindObservable[Document] = collection.find()

    //    val seq = findObservable.results()
    //    seq.foreach(doc => {
    //      println("==> "+ doc)
    //    })

    import scala.concurrent.ExecutionContext.Implicits.global

    val future = findObservable.toFuture()

    import scala.compat.java8.FutureConverters._

    val javaFuture = future.toJava
//    javaFuture.thenAcceptAsync(new Consumer[Seq[Document]] {
//      override def accept(t: Seq[Document]): Unit = {
//        t.foreach(tt => {
//          println("Java Future ==> "+ tt)
//        })
//      }
//    })

    val completableFuture = javaFuture
      .asInstanceOf[CompletableFuture[Seq[Document]]]
    val result = completableFuture.get()

    println("result =============> "+ result)

    //val result = Await.result(future, Duration(10, TimeUnit.SECONDS))

    //result.foreach(x => println(x))

    import scala.concurrent.CanAwait


    var i = 1
    future.onComplete(tsd => {
      tsd.foreach(doc => {
        i += 1
        println("doc ==> "+ doc)
        println(" i = " + i)
      })
    })



    mongo.close()

    TimeUnit.SECONDS.sleep(5L)
    println("==  完[Done] ==")
  }
}
