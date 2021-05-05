package com.gitHub.scalaKafkaSandbox.basics.kafkaStreams

import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.apache.kafka.streams.scala.kstream.Materialized
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.scala.serialization.Serdes._

import java.util.Properties
import scala.concurrent.duration.DurationInt
import scala.jdk.DurationConverters.ScalaDurationOps

object StreamWordCount extends App {

  val props: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
    p
  }

  implicit val materialized: Materialized[String, Long, ByteArrayKeyValueStore] = Materialized.as("counts-store")

  val builder: StreamsBuilder = new StreamsBuilder
  val textLines: KStream[String, String] = builder.stream[String, String]("TextLinesTopic")
  val wordCounts: KTable[String, Long] = textLines
    .flatMapValues(textLine => textLine.toLowerCase.split("\\W+"))
    .groupBy((_, word) => word)
    .count()(materialized)
  wordCounts.toStream.to("WordsWithCountsTopic")

  val streams: KafkaStreams = new KafkaStreams(builder.build(), props)
  streams.start()

  sys.ShutdownHookThread {
    streams.close(10.second.toJava)
  }
}

/**
 * Create source topic
 * docker-compose exec broker1 kafka-topics --create --topic TextLinesTopic --bootstrap-server broker1:9092 --replication-factor 1 --partitions 1
 *
 * Create target topic
 * docker-compose exec broker1 kafka-topics --create --topic WordsWithCountsTopic --bootstrap-server broker1:9092 --replication-factor 1 --partitions 1
 *
 * Produce message in source topic
 * docker-compose exec broker1 kafka-console-producer --topic TextLinesTopic --bootstrap-server broker1:9092
 *
 * Consume message in target topic
 * docker-compose exec broker1 kafka-console-consumer --topic WordsWithCountsTopic --bootstrap-server broker1:9092
 */
