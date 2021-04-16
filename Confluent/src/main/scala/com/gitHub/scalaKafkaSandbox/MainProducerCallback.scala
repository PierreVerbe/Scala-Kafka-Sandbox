package com.gitHub.scalaKafkaSandbox

import com.gitHub.scalaKafkaSandbox.producerCallback.KafkaProducerCallbackApplication
import com.gitHub.scalaKafkaSandbox.toolKafka.loadProperties
import org.apache.kafka.clients.producer.KafkaProducer

object MainProducerCallback {

  def main(args: Array[String]): Unit = {
    val messages = List("1-hello",
      "2-world",
      "3-apache",
      "4-kafka")

    val props = loadProperties("Confluent/src/main/resources/configuration/dev_producer.properties")
    val topic = props.getProperty("output.topic.name")
    val producer = new KafkaProducer[String, String](props)
    val producerApp = new KafkaProducerCallbackApplication(producer, topic)

    messages.filter(_.isEmpty).map(producerApp.produce)
    producerApp.shutdown()
  }

}
