package com.gitHub.scalaKafkaSandbox.kafkaStreams

import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util.{Collections, Properties}
import scala.jdk.CollectionConverters.IterableHasAsScala

object ConsumeWordCount extends App {

  val TOPIC = "WordsWithCountsTopic"

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer")
  props.put("group.id", "something")

  val consumer = new KafkaConsumer[String, Long](props)

  consumer.subscribe(Collections.singletonList(TOPIC))

  while (true) {
    val records = consumer.poll(100).asScala
    for (record <- records.iterator) {
      println(record)
    }
  }

}
