package com.example.myscala.utils.notecoll

import scala.collection.mutable
import scala.collection.mutable.AnyRefMap.AnyRefMapBuilder
import scala.collection.immutable
import scala.collection.mutable.ArrayBuffer

class Demo001 {


  def runSecond(): Unit = {

    val x = "1" -> "一"
    val y = ("1" -> "壹")

    println(" x = " + x.getClass)
    println(" y = " + y.getClass)


    val anyRefMapBuilder = new AnyRefMapBuilder[String, String]

    anyRefMapBuilder += ("1" -> "hello")


    val anyRefMap: mutable.AnyRefMap[String, String] = anyRefMapBuilder.result()

    anyRefMap += ("2", "world")

    println(anyRefMap)


    val anyRefMap2 = mutable.AnyRefMap(
      "hello" -> "world",
      "are you" -> "OK",
      "I" -> "am fain",
      "Thank" -> "you very much"
    )


    anyRefMap2("Cheng") = "Chao"
    anyRefMap2("Han") = "Dong"
    anyRefMap2("Gao") = "继原"


    println(anyRefMap2)

    val str1: Option[String] = anyRefMap2.get("I")
    println("str1 => " + str1)
    str1 match {
      case Some(str) => println(s"I -=> $str")
      case None => println("str1 value is nothing")
    }

    try {
      val str2: String = anyRefMap2("Thank")
      println(s"Thank >>--> $str2")
    } catch {
      case ex: Exception => ex.printStackTrace()
    }


    val feeds1 = Set("blog.toolshed.com", "pragdave.pragprog.com", "pragrmactic-osxer.blogspot.com", "vita-contemplativa.blogspot.com")
    val feeds2: immutable.Set[String] = Set("blog.toolshed.com", "martinfowler.com/bliki")

    println(feeds2.getClass)
    val blogSpotFeeds = feeds1 filter (_ contains "blogspot")
    println("blogspot feeds: " + blogSpotFeeds.mkString(", "))

    // 合并两个 Set
    val mergedFeeds = feeds1 ++ feeds2
    println("# of merged feeds: " + mergedFeeds)

    val listFeeds = List("blog.toolshed.com",
      "pragdave.pragprog.com",
      "dimsumthinking.com/blog")
    println("First feed in list => " + listFeeds.head)
    println("Second feed in list => " + listFeeds(1))

    val listFeeds2 = "formus.pragprog.com/formus/87" :: listFeeds
    println("First Feed In listFeeds2: " + listFeeds2.head)

    val listFeed3 =
      "pargdave.pragprog.com" ::
        "dimsumthinking.com/blog" ::
        Nil

    println("listFeed3 => " + listFeed3.getClass)

    val total = listFeeds2.foldLeft(0) { (total, feed) => total + feed.length }
    println("Total length of feed urls: => " + total)
    val total2 = (0 /: listFeed3) {
      _ + _.length
    }
    println("Total2 => " + total2)

    val total3 = (listFeeds :\ 0) {
      _.length + _
    }
    println("Total3 => " + total3)

    var ab: ArrayBuffer[String] = mutable.ArrayBuffer("hello", "world")
    var xxx: ArrayBuffer[String] = ab
    for (i <- 1 to 3) {
      println(" i = " + i)
    }
    println(s" ab => $ab")

    val result = for (i <- 0 until 10) yield i * 2
    println(result)

    val listBufferBuilder: mutable.Builder[String, List[String]] = immutable.List.newBuilder[String]

    for (i <- 1 to 3) {
      listBufferBuilder += "hello" + i
    }

    val stringList: List[String] = listBufferBuilder.result()
    println("stringList => " + stringList.getClass)
    println("stringList => " + stringList)

    val x1 = "1"
    java.lang.Integer.valueOf(x1, 10)

  }


  def runFirst(): Unit = {


    val colors1 = Set("blue", "green", "red")
    var colors = colors1

    println("colors1 (colors): " + colors)

    colors += "Black"
    println("colors: " + colors)
    println("colors1: " + colors1)

    val ints = List(1, 2, 3, 4, 5)

    val iterator: Iterator[List[Int]] = ints.sliding(2)
    println("--end--")
  }

}


object Demo001 {
  def main(args: Array[String]): Unit = {
    val demo001 = new Demo001()
    //    demo001.runFirst()
    demo001.runSecond()
  }
}