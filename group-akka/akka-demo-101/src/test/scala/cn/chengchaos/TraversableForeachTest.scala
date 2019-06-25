package cn.chengchaos

import org.junit.Test

/**
  * foreach 是最基本的高阶函数
  */
class TraversableForeachTest {

  @Test
  def test001() :Unit = {

    val s = Traversable(1, 2, 3, 4)
    s.foreach(x => x * x)

    s.foreach(println)

    s.foreach(println(_))

    s.foreach(x => println(x + 3))

  }

  @Test
  def flattenTest(): Unit = {

    val t1 = Traversable(
      Traversable(1, 2, 3),
      Traversable(4, 5, 6),
      Traversable(7, 8, 9)
    )

    val t2 = t1.flatten

    println("t2 => "+ t2)
  }

  /**
    * 转置 Traversable 集合
    */
  @Test
  def transposeTest(): Unit = {

    val matrix = Traversable(
      Traversable(1, 2, 3),
      Traversable(4, 5, 6),
      Traversable(7, 8, 9)
    )

    val result= matrix.transpose

    println(result)
  }

  @Test
  def unzipTest(): Unit = {

    val t1 = Traversable(
      "a" -> 1,
      "b" -> 2,
      "c" -> 3
    )

    val (a, b) = t1.unzip
    println(a)
    println(b)

    val t2 = Traversable("a_1", "b_2", "c_3")
    val (a2, b2) = t2.unzip(x => (x(0), x.substring(2).toInt))

    println(a2)
    println(b2)

  }


  /**
    * 使用隐式转换
    */
  @Test
  def unzipTest2(): Unit = {

    implicit def asPair(x: String) = (x(0), x.substring(2).toInt)

    val t2 = Traversable("a_1", "b_2", "c_3")
    val (a2, b2) = t2.unzip

    println(a2)
    println(b2)

  }


  /**
    * 连接两个 Traversable
    * `++` 方法， 返回的类型与左侧的变量一致。
    * `++:` 方法， 返回的类型与右侧的变量一致。
    */
  @Test
  def concat1Test(): Unit = {

    val movieNames = Traversable("A", "b", "c")
    val ids = Traversable(1, 2, 3)

    val x = movieNames ++ ids

    println(x)
  }

  /**
    * 偏函数是一个一元函数。
    * 只处理定义域上的一部分
    * 可以根据 `isDefinedAt` 来判断一个元素能否被这个偏函数处理。
    *
    * `collect` 是一个高阶函数。
    * 它利用一个偏函数收集 `isDefinedAt` 等于 `true` 的元素。
    * 并进行处理，再将结果放到一个新的集合中。类型可以不同。
    *
    * 兼有 `filter` 和 `map` 的功能。
    */
  @Test
  def partialFunctionTest(): Unit = {

    val t1 = Traversable(1 to 100 : _*)
    def filterEven: PartialFunction[Int, Int] = {
      case x if x % 2 == 0 => x
    }

    val result = t1.collect(filterEven)
    println(result)

    val result2 = t1 collect {
      case x if x % 2 != 0 => x
    }

    println(result2)

  }
}
