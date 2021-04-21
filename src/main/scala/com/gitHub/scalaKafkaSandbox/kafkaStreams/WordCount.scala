package com.gitHub.scalaKafkaSandbox.kafkaStreams

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.scala.{Serdes, StreamsBuilder}
import org.apache.kafka.streams.scala.kstream.{KStream, KTable}

import java.util.Properties
import java.util.concurrent.TimeUnit

object WordCount extends App {

/*
  val props: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    p
  }

  // Serializers/deserializers (serde) for String and Long types
  final val stringSerde: Serde[String] = Serdes.String
  final val longSerdeSerde: Serde[Long] = Serdes.Long

  val builder: StreamsBuilder = new StreamsBuilder
  val textLines: KStream[String, String] = builder.stream[String, String]("WordsTopic")
  val wordCounts: KTable[String, Long] = textLines
    .flatMapValues(textLine => textLine.toLowerCase.split("\\W+"))
    .groupBy((_, word) => word)
    .count()(Materialized.as("counts-store"))
  wordCounts.toStream.to("WordsWithCountsTopic")

  val streams: KafkaStreams = new KafkaStreams(builder.build(), props)
  streams.start()

  sys.ShutdownHookThread {
    streams.close(10, TimeUnit.SECONDS)


  }

 */


}
