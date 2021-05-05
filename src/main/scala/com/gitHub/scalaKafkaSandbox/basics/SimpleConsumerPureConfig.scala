package com.gitHub.scalaKafkaSandbox.basics

import com.gitHub.scalaKafkaSandbox.config.Configuration
import com.gitHub.scalaKafkaSandbox.config.Configuration.ConsumerConf
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.Serdes
import org.slf4j.{Logger, LoggerFactory}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import java.util.Collections
import scala.collection.mutable
import scala.concurrent.duration.DurationInt
import scala.jdk.CollectionConverters.IterableHasAsScala
import scala.jdk.DurationConverters.ScalaDurationOps
import scala.util.Try

object SimpleConsumerPureConfig extends App with Configuration {

  def printMetaData(consumerRecords: ConsumerRecords[String, String]): Unit =
    consumerRecords.forEach(record => {
      println(
        s"""Offset: ${record.offset()}
           |Timestamp: ${record.timestamp()}
           |Value: ${record.value()}""".stripMargin.replace("\n", "")
      )
    })

  def consume(consumer: KafkaConsumer[String, String]): Vector[String] = {
    val words: ConsumerRecords[String, String] = consumer.poll(1.second.toJava)
    printMetaData(words)
    words.asScala.toVector.map(_.value())
  }

  private def logger: Logger = LoggerFactory.getLogger(getClass)

  private val consumerConf = ConfigSource.file("src/main/resources/application/application.conf").at("consumer").loadOrThrow[ConsumerConf]
  val wordMap = mutable.Map[String, String]()

  val threadConsumer = new Thread {
    override def run {

      logger.info("creating the kafka consumer")
      val consumer = new KafkaConsumer[String, String](
        consumerConf.clientConfig.toProperties,
        Serdes.String().deserializer(),
        Serdes.String().deserializer()
      )

      consumer.subscribe(Collections.singletonList(consumerConf.topics.topicSpec.name))

      while (this.isAlive) {
        Thread.sleep(2.second.toMillis)

        logger.debug("polling the new events")
        val words: Vector[String] = consume(consumer)

        if (words.nonEmpty) logger.info(s"just polled ${words.size} books from kafka")
        words.foreach { word =>
          wordMap += word.length.toString -> word
          logger.info(s"${word}")
        }
      }

      logger.info("Closing the kafka consumer")
      Try(consumer.close()).recover {
        case error => logger.error("Failed to close the kafka consumer", error)
      }
    }
  }
  threadConsumer.start()

  sys.addShutdownHook {
    threadConsumer.join(1000)
  }
}
