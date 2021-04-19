package com.gitHub.scalaKafkaSandbox.consumerApp

import org.apache.kafka.clients.consumer.ConsumerRecords

trait ConsumerRecordsHandler[K, V] {
  def process(consumerRecords: ConsumerRecords[K, V]): Unit
}
