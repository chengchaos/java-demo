package com.example.demo.controller

import java.io.{BufferedReader, InputStreamReader}
import java.security.MessageDigest
import java.util
import java.util.concurrent.Callable

import com.alibaba.fastjson.JSON
import grizzled.slf4j.Logger
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.apache.commons.codec.binary.Hex
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RestController}
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.request.async.WebAsyncTask
import org.springframework.web.util.UriComponentsBuilder

import scala.io.Source
import scala.xml.{Elem, PCData, Text, XML}

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/7/8 11:40 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@RestController
class WechatController(@Autowired restTemplate: RestTemplate,
                       @Autowired threadPoolTaskExecutor: ThreadPoolTaskExecutor) {

  private val logger: Logger = Logger(classOf[WechatController])

  private val appId: String = "wxb4999cc5ce3b3bf8"
  private val appSecret: String = "7f5a5c179a0f7b99bbcde055a2682111"


  def retrieveAccessToken(): java.util.Map[String, Object] = {

    val url = s"""https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${appId}&secret=$appSecret"""
    val uri = UriComponentsBuilder.fromUriString(url)
      .build()
      .encode()
      .toUri

    val requestEntity = restTemplate.getForEntity(uri, classOf[String])
    val statusCode = requestEntity.getStatusCode
    val statusCodeValue = statusCode.value()

    logger.info(s"status code -=> $statusCodeValue")

    val body = requestEntity.getBody
    logger.info(s"body -=> $body")

    // 正常时返回: {"access_token":"ACCESS_TOKEN","expires_in":7200}
    // 出错时返回: {"errcode":40013,"errmsg":"invalid appid"}
    val jsonObject = JSON.parseObject(body)

    jsonObject
  }

  @GetMapping(value = Array("/_refresh"))
  def refresh(): java.util.Map[String, Object] = {
    this.retrieveAccessToken()

  }

  private val token: String = "WhatTheFuckCanIDo"

  private val algorithm: String = "SHA-1"


  def createSignature(timestamp: String, nonce: String): Either[Boolean, String] = {
    if (StringUtils.isNoneBlank(timestamp, nonce)) {
      //val list = util.Arrays.asList(token, timestamp, nonce)
      //list.sort(null)

      val arrays: Array[String] = Array(token, timestamp, nonce)
      util.Arrays.sort(arrays.asInstanceOf[Array[Object]])
      val clearText = arrays.foldLeft(StringBuilder.newBuilder) {
        (sb, str) => sb.append(str)
      }.toString()

      //val clearText = arrays(0) + arrays(1) + arrays(2)
      val messageDigest = MessageDigest.getInstance(algorithm)
      val digest = messageDigest.digest(clearText.getBytes)
      val encodedHex = Hex.encodeHex(digest)
      val sign = new String(encodedHex)
      logger.info(s"sign -=> $sign")

      Right(sign)

    } else {
      Left(false)
    }
  }

  def checkSignature(left: String, right: String): Boolean = {
    left == right
  }

  def service(signature: String,
              echostr: String,
              timestamp: String,
              nonce: String): WebAsyncTask[String] = {
    new WebAsyncTask[String](5000L, threadPoolTaskExecutor, new Callable[String] {
      override def call(): String = {

        logger.info(s"get : signature -=> $signature")
        logger.info(s"get : echostr-=> $echostr")
        logger.info(s"get : timestamp -=> $timestamp")
        logger.info(s"get : nonce-=> $nonce")

        val signEither = createSignature(timestamp, nonce)

        val checkResult = signEither match {
          case Right(mySign) => checkSignature(mySign, signature)
          case _ => false
        }

        if (checkResult) echostr
        else ""
      }
    })
  }

  @GetMapping(value = Array("/service"))
  def serviceGet(signature: String,
                 echostr: String,
                 timestamp: String,
                 nonce: String,
                 req: HttpServletRequest,
                 resp: HttpServletResponse): WebAsyncTask[String] = {

    service(signature, echostr, timestamp, nonce)

  }

  @PostMapping(value = Array("/service"),
    produces = Array(MediaType.APPLICATION_XML_VALUE)
  )
  def servicePost(signature: String,
                  openid: String,
                  timestamp: String,
                  nonce: String,
                  req: HttpServletRequest,
                  resp: HttpServletResponse): String = {

    logger.info(s"param : signature -=> $signature")
    logger.info(s"param : openid -=> $openid")
    logger.info(s"param : timestamp -=> $timestamp")
    logger.info(s"param : nonce-=> $nonce")

    val mySignature = this.createSignature(timestamp, nonce)

    val checkResult = mySignature match {
      case Right(mysign) => this.checkSignature(mysign, signature)
      case _ => false
    }

    if (checkResult) {
      val opt: Option[Elem] = this.readXml(req)

      opt match {
        case Some(inputElem) =>
          val msgTypeSeq = inputElem \ "MsgType"
          if (msgTypeSeq.length > 0) {
            val msgTypeNode = msgTypeSeq.head
            val msgType = msgTypeNode.text

            val respStr = this.createTextMsg(inputElem, "你好，大侠")
            logger.info(s"resp str -=> $respStr")
            respStr.toString()
          } else {
            StringUtils.EMPTY
          }
        case _ => StringUtils.EMPTY
      }

    } else {
      ""
    }

    // val inputStream = req.getInputStream
    //    val length = inputStream.available()
    //    val reader = new BufferedReader(new InputStreamReader(inputStream))
    //
    //    var line: String = null
    //
    //    val buffer: StringBuilder = new StringBuilder(length, StringUtils.EMPTY)
    //    do {
    //      line = reader.readLine()
    //      if (line != null) {
    //        buffer.append(line)
    //      }
    //    } while (line != null)
    //
    //    logger.info(s"input -=> $buffer")

    // scala.io.Source.fromInputStream(is).getLines().mkString("\n")
    //val inputXml = Source.fromInputStream(inputStream).mkString
    //logger.info(s"input xml -=> $inputXml")


  }

  def readXml(request: HttpServletRequest): Option[Elem] = {

    val is = request.getInputStream
    try {
      val xml = XML.load(is)
      Some(xml)
    } catch {
      case e: Exception => logger.error(StringUtils.EMPTY, e)
        None
    } finally {
      if (is != null) {
        is.close()
      }
    }
  }

  def createTextMsg(inputElem: Elem, message: String): Elem = {
    val toUserName = inputElem \ "ToUserName"
    val fromUserName = inputElem \ "FromUserName"
    val createTime = inputElem \ "CreateTime"
    val msgType = inputElem \ "MsgType"
    val content = inputElem \ "Content"
    val msgId = inputElem \ "MsgId"


    val respCreateTime = (System.currentTimeMillis() / 1000).toString
    <xml>
      <ToUserName>
        {PCData(fromUserName.text)}
      </ToUserName>
      <FromUserName>
        {PCData(toUserName.text)}
      </FromUserName>
      <CreateTime>
        {Text(respCreateTime)}
      </CreateTime>
      <MsgType>
        {PCData("text")}
      </MsgType>
      <Content>
        {PCData(message)}
      </Content>
    </xml>


  }


}
