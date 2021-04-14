package com.gitHub.scalaKafkaSandbox.basics

import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util.{Collections, Properties}
import scala.jdk.CollectionConverters.IterableHasAsScala

object SimpleConsumer extends App {

  val TOPIC = "test"

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:29092")

  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(Collections.singletonList(TOPIC))

  while (true) {
    val records = consumer.poll(100).asScala
    for (record <- records.iterator) {
      println(record)
    }
  }

}
