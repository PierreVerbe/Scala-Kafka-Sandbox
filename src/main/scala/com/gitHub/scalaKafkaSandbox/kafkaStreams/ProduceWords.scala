package com.gitHub.scalaKafkaSandbox.kafkaStreams

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.Properties

object ProduceWords extends App {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val TOPIC = "WordsTopic"

  while (true) {
    val record = new ProducerRecord(TOPIC, "key", s"hello apache kafka")
    producer.send(record)
    //wait(500)
  }

  producer.close()

}
