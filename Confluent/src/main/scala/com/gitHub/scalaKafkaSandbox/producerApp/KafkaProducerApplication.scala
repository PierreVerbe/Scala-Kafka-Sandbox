package com.gitHub.scalaKafkaSandbox.producerApp

import org.apache.kafka.clients.producer.{Producer, ProducerRecord, RecordMetadata}

import java.util.concurrent.Future

class KafkaProducerApplication(val producer: Producer[String, String], val outTopic: String) {

  def produce(message: String): Future[RecordMetadata] = {
    val parts: Array[String] = message.split("-")
    val (key, value) = if (parts.length > 1) (parts(0), parts(1)) else (null, parts(0))

    val producerRecord = new ProducerRecord[String, String](outTopic, key, value)
    producer.send(producerRecord)
  }

  def printMetadata(metadata: List[Future[RecordMetadata]]): Unit = {
    metadata.foreach(m => {
      try {
        val recordMetadata = m.get
        System.out.println("Record written to offset " + recordMetadata.offset + " timestamp " + recordMetadata.timestamp)
      } catch {
        case _: InterruptedException => Thread.currentThread.interrupt()
      }
    })
  }

  def shutdown(): Unit = {
    producer.close()
  }

}
