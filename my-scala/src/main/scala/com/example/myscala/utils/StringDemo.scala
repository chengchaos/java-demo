package com.example.myscala.utils

class StringDemo {


  def runDemo(): Unit = {

    val s1 = "Hello"
    val s2 = "Hello"

    val s3 = "H" + "ello"

    println(s1 == s2)

    println(s1 == s3)

    println(s1 == null)

    println(s1.toUpperCase)


    val foo =
      """this is a multiline
        #String
      """.stripMargin('#')

    println(foo)

    "hello, world, butter, Coco Puffs".split(",").map(_.trim).foreach(println)
  }

}
