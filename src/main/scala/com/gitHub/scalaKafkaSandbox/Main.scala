package com.gitHub.scalaKafkaSandbox

import com.gitHub.scalaKafkaSandbox.toolKafka.loadProperties
import org.apache.kafka.clients.producer.KafkaProducer

object Main {
  def main(args: Array[String]): Unit = {
    val messages = List("1-hello",
      "2-world",
      "3-apache",
      "4-kafka")

    val props = loadProperties("src/main/resources/configuration/dev.properties")
    val topic = props.getProperty("output.topic.name")
    val producer = new KafkaProducer[String, String](props)
    val producerApp = new KafkaProducerApplication(producer, topic)

    val listFutureRecordMetadata = messages.filter(_.isEmpty).map(producerApp.produce)
    producerApp.printMetadata(listFutureRecordMetadata)

    producerApp.shutdown()
  }
}
