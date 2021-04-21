package com.gitHub.scalaKafkaSandbox.basics

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import java.time.Duration
import java.util.{Collections, Properties}
import scala.jdk.CollectionConverters.IterableHasAsScala

object SimpleConsumer extends App {

  val TOPIC = "output-topic"

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:29092")
  props.put("group.id", "something")
  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(Collections.singletonList(TOPIC))

  while (true) {
    val records = consumer.poll(Duration.ofSeconds(1)).asScala
    for (record <- records.iterator) {
      println(record.value())
    }
  }

}
