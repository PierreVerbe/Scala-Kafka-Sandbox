package com.gitHub.scalaKafkaSandbox

import com.gitHub.scalaKafkaSandbox.consumerApp.KafkaConsumerApplication
import com.gitHub.scalaKafkaSandbox.toolKafka.loadProperties
import org.apache.kafka.clients.consumer.KafkaConsumer

object MainConsumer {

  def main(args: Array[String]): Unit = {
    val props = loadProperties("Confluent/src/main/resources/configuration/dev_consumer.properties")
    val consumer = new KafkaConsumer[String, String](props)
    val consumerApplication = new KafkaConsumerApplication(consumer)

    sys.addShutdownHook(consumerApplication.shutdown())
    consumerApplication.runConsume(props)
  }

}
