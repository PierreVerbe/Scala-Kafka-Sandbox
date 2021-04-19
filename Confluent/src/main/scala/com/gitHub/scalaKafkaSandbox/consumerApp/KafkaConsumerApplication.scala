package com.gitHub.scalaKafkaSandbox.consumerApp

import org.apache.kafka.clients.consumer.Consumer

import java.time.Duration
import java.util.{Collections, Properties}

class KafkaConsumerApplication(val consumer: Consumer[String, String], val recordsHandler: ConsumerRecordsHandler[String, String]) {

  @volatile
  private var keepConsuming = true

  def runConsume(consumerProps: Properties): Unit = {
    try {
      consumer.subscribe(Collections.singletonList(consumerProps.getProperty("input.topic.name")))
      while (keepConsuming) {
        val consumerRecords = consumer.poll(Duration.ofSeconds(1))
        recordsHandler.process(consumerRecords)
      }
    } finally consumer.close()
  }

  def shutdown(): Unit = {
    keepConsuming = false
  }

}
