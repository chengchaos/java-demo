package cn.futuremove.tsp.tbox.helper

import io.netty.buffer.{ByteBuf, ByteBufAllocator, ByteBufUtil, Unpooled}
import org.junit.Test

import scala.annotation.{switch, tailrec}

class HelperTest {


  private val x = "23230201464d542d434537313830373133303133360100af00010108380b01ffffffffffffffffffffffffffffffffffffff000201ffffffffffffffffffffffff0501000000000000000006ffffffffffffffffffffffffffff0700000000000000000008ff09ff86000800ffffffffffffff870010000000000101010101000000000000ff8800100000000000000028010100000000000089000cffffffff00ff00ffffffffff81001c00000000000043453631305244303039434536313000000000010000ba"

  def hexStringToBytes(hexString: String): Array[Byte] = {

    if (hexString == null || hexString == "") return null

    val hexWords: String = "0123456789ABCDEF"
    val length = hexString.length >> 1
    val charArrays: Array[Char] = hexString.toUpperCase.toCharArray
    val arrays:Array[Byte] = new Array[Byte](length)
    for (i <- 0 until length) {
      val pos = i << 1
      arrays(i) = (hexWords.indexOf(charArrays(pos)) << 4 | hexWords.indexOf(charArrays(pos + 1))).toByte
    }
    arrays
  }

  @Test
  def checkTest() : Unit = {



    val bytes = hexStringToBytes(x)

    val byteBuf = Unpooled.copiedBuffer(bytes)
    val pretty = ByteBufUtil.prettyHexDump(byteBuf)
    println(pretty)

    val length = x.length
    println(length)
    println(length /2)
    println(length >> 1)

    for (i <- 0 until  10) {
      println(i)
    }
  }

  @Test
  def optionTest() : Unit = {

    val name:Null = null

    val opt = Option(name)

//    val res = this.execOption(opt)
//    println(res)

    (opt: @switch) match {
      case Some(value) => println("v = "+ value)
      case None => println("wokao")
    }
  }

  def execOption(opt: Option[String]): String = (opt: @switch) match {
    case Some(value) => println("v = "+ value); "OK"
    case None => println("wokao"); "not ok"
  }
}
