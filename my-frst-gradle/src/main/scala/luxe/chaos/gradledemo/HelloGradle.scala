package luxe.chaos.gradledemo

import java.util
import java.util.Objects

import com.sun.istack.internal.NotNull


object HelloGradle {

  def main(args: Array[String]): Unit = {
    val user = new User(1, "chengchao")
    println(s"hello ...$user")

    var javaList = util.Arrays.asList("chengchao", "handong", "gaojiyuan")

    javaList.stream()
      .filter(str => Objects.nonNull(str))
      .map(str => str.toUpperCase())
      .forEach(str => println(str))

  }
}