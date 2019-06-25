package cn.chengchaos

import org.junit.Test

import scala.collection.immutable.{HashMap, TreeSet}
import scala.reflect.runtime.{universe => ru}

/**
  * 如何初始化一个 Traversable 对象？
  * 通过 Traversable 的一个具体实现类的伴生对象来获取。
  *
  */
class TraversableTest001 {

  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag

  @Test
  def test001() :Unit = {

    val t1 = List(1, 2, 3).toTraversable
    val t2 = TreeSet(1, 2, 3).toTraversable
    val t3 = HashMap('a' -> 97, 'b' -> 98, 'c' -> 99).toTraversable
    val t4 = "String".toTraversable

    val x = (0 /: t1) { _ + _}
    println(" x = "+ x)

    // 或者， 直接赋值给一个 Traversable 变量

    val t6:Traversable[Int] = List(1, 2, 3, 4)

    val y = (t6 :\ 0) { _ + _ }
    println(" y = "+ y)

    val s = Traversable(1, 2, 3)
    println(s.getClass)

    println(getTypeTag(s).tpe)
  }
}
