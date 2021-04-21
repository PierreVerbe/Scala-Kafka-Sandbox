package com.gitHub.scalaKafkaSandbox.consumerApp

import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.streams.KeyValue

import scala.collection.mutable.ListBuffer

class FileWritingRecordsHandler extends ConsumerRecordsHandler[String, String] {
  var listBufferRecords = new ListBuffer[KeyValue[String, String]]

  override def process(consumerRecords: ConsumerRecords[String, String]): Unit = {
    consumerRecords.forEach(record => {
      println("Record written to offset " + record.offset() + " timestamp " + record.timestamp() + " value " + record.value())
      listBufferRecords.addOne(KeyValue.pair(record.key(), record.value()))
    })
  }

}
