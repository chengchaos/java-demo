package com.example.demo.controller

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream}

import org.junit.Test

import scala.xml.{Elem, PCData}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/7/10 11:25 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
class XmlTest {


  @Test
  def proceXml() : Unit = {

    val xml = "<xml>"+
      "<ToUserName><![CDATA[gh_02d64c00887b]]></ToUserName>" +
      "<FromUserName><![CDATA[oFIu1t5vNvqLO6IrsBWuIJieRNSM]]></FromUserName>" +
      "<CreateTime>1562728242</CreateTime>" +
      "<MsgType><![CDATA[text]]></MsgType>" +
      "<Content><![CDATA[You ]]></Content>" +
      "<MsgId>22372890019618565</MsgId>" +
      "</xml>"
    import scala.xml.XML

    val root:Elem = XML.load(new ByteArrayInputStream(xml.getBytes))


    println(root)
    val toUserName = (root \ "ToUserName").head
    val fromUserName = root \ "FromUserName"
    val createTime = root \ "CreateTime"
    val msgType = root \ "MsgType"
    val content = root \ "Content"
    val msgId = root \ "MsgId"

    val resp = <xml>
      <ToUserName>{PCData(fromUserName.text)}</ToUserName>
      <FromUserName>{PCData(toUserName.text)}</FromUserName>
      <MsgType>text</MsgType>
      <Content>{PCData("你好")}</Content>
    </xml>

    //resp.reduceLeft((str, node) => str + node.toString())

    println("resp = "+ resp)
  }


  def cdata(value: String) : String = {
    s"<![CDATA[$value]]>"
  }

}
