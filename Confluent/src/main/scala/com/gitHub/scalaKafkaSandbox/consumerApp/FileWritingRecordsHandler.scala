package com.gitHub.scalaKafkaSandbox.consumerApp

import org.apache.kafka.clients.consumer.ConsumerRecords

import scala.collection.mutable.ListBuffer

class FileWritingRecordsHandler extends ConsumerRecordsHandler[String, String] {
  var listBufferRecords = new ListBuffer[(String, String)]

  override def process(consumerRecords: ConsumerRecords[String, String]): Unit = {
    consumerRecords.forEach(record => {
      println("Record written to offset " + record.offset() + " timestamp " + record.timestamp() + " value " + record.value())
      listBufferRecords.addOne((record.key(), record.value()))
    })
    println(listBufferRecords.length)
  }

}
