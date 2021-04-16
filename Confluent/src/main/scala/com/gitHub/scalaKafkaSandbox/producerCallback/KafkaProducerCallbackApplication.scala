package com.gitHub.scalaKafkaSandbox.producerCallback

import org.apache.kafka.clients.producer.{Callback, Producer, ProducerRecord, RecordMetadata}

import java.util.concurrent.Future

class KafkaProducerCallbackApplication(val producer: Producer[String, String], val outTopic: String) {

  def produce(message: String): Future[RecordMetadata] = {
    val parts: Array[String] = message.split("-")
    val (key, value) = if (parts.length > 1) (parts(0), parts(1)) else (null, parts(0))

    val producerRecord = new ProducerRecord[String, String](outTopic, key, value)
    producer.send(producerRecord, new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        if (exception == null) {
          println("Record written to offset " +
            metadata.offset + " timestamp " +
            metadata.timestamp)
        }
        else {
          println("An error occurred")
          exception.printStackTrace(System.err)
        }
      }
    })
  }

  def shutdown(): Unit = {
    producer.close()
  }

}
