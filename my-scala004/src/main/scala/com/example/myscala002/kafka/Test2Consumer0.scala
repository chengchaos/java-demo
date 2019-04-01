package com.example.myscala002.kafka

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.slf4j.LoggerFactory

class Test2Consumer0 {

  private val logger = LoggerFactory.getLogger(classOf[Test2Consumer0])

  private val flag = true

  private def configure() :Properties = {

    val props = new Properties()

    // "bootstrap.servers"
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "")
    // group.id
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "chaos")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeseerializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    props
  }



}
