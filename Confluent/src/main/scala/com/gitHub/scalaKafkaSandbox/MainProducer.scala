package com.gitHub.scalaKafkaSandbox

import com.gitHub.scalaKafkaSandbox.producerApp.KafkaProducerApplication
import com.gitHub.scalaKafkaSandbox.tool.KafkaTool.loadProperties
import org.apache.kafka.clients.producer.KafkaProducer

object MainProducer {

  def main(args: Array[String]): Unit = {
    val messages = List("1-hello",
      "2-world",
      "3-apache",
      "4-kafka")

    val props = loadProperties("Confluent/src/main/resources/configuration/dev_producer.properties")
    val topic = props.getProperty("input.topic.name")
    val producer = new KafkaProducer[String, String](props)
    val producerApp = new KafkaProducerApplication(producer, topic)

    val listFutureRecordMetadata = messages.filter(_.nonEmpty).map(producerApp.produce)
    producerApp.printMetadata(listFutureRecordMetadata)

    producerApp.shutdown()
  }

}
