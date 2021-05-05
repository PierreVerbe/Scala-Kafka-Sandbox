package com.gitHub.scalaKafkaSandbox.basics

import com.gitHub.scalaKafkaSandbox.config.Configuration
import com.gitHub.scalaKafkaSandbox.config.Configuration.ProducerConf
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.Serdes
import org.slf4j.{Logger, LoggerFactory}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import java.util.concurrent.Future
import scala.concurrent.duration._

object SimpleProducerPureConfig extends App with Configuration {

  def printMetaData(metadata: RecordMetadata): String =
    s"""topic: ${metadata.topic()},
       | partition: ${metadata.partition()},
       | offset: ${metadata.offset()}
       | (ts: ${metadata.timestamp()})""".stripMargin.replace("\n", "")

  def produce(producer: KafkaProducer[String, String], topic: String, word: String): Future[RecordMetadata] = {
    val record: ProducerRecord[String, String] = new ProducerRecord(topic, word)

    producer.send(record, new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = Option(exception)
        .map(ex => logger error s"fail to produce record due to: ${ex.getMessage}")
        .getOrElse(logger info s"successfully produced - ${printMetaData(metadata)}")
    })
  }

  private val enabledStdIn = false
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  private val producerConf = ConfigSource.file("src/main/resources/application/application.conf").at("producer").loadOrThrow[ProducerConf]

  logger.info("creating the kafka producer")

  private val producer = new KafkaProducer[String, String](
    producerConf.clientConfig.toProperties,
    Serdes.String.serializer(),
    Serdes.String.serializer()
  )

  logger.info("creating the serializer and configuration")

  if (enabledStdIn) {
    var input = ""
    while (input != "exit") {
      Thread.sleep(2.second.toMillis)
      input = scala.io.StdIn.readLine()
      if (input != "exit") produce(producer, producerConf.topics.topicSpec.name, input)
    }
  }
  else {
    val inputSeqWords = Seq("Hello", "World", "Apache", "Kafka")

    inputSeqWords.foreach { word =>
      Thread.sleep(2.second.toMillis)
      produce(producer, producerConf.topics.topicSpec.name, word)
    }
  }

  logger warn "closing the word producer application"
  producer.flush()
  producer.close()
}
