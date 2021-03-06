package com.gitHub.scalaKafkaSandbox.basics

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.{Date, Properties}

object SimpleProducer extends App {
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:29092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val topicName = "output-topic"

  for (i <- 1 to 50) {
    val record = new ProducerRecord[String, String](topicName, "key", s"hello kafka $i")
    producer.send(record)
  }

  val lastRecord = new ProducerRecord[String, String](topicName, "key", s"the end " + new Date())
  producer.send(lastRecord)

  producer.close()
}
