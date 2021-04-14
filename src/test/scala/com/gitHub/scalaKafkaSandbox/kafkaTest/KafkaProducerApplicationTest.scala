package com.gitHub.scalaKafkaSandbox.kafkaTest

import com.gitHub.scalaKafkaSandbox.KafkaProducerApplication
import com.gitHub.scalaKafkaSandbox.toolKafka.loadProperties
import org.apache.kafka.clients.producer.{MockProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.streams.KeyValue
import org.scalatest.FunSuite

import scala.jdk.CollectionConverters._

class KafkaProducerApplicationTest extends FunSuite {

  final val TEST_CONFIG_FILE = "src/test/resources/configuration/test.properties"

  test("Testing KafkaProducerApplication"){
    // Given
    val props = loadProperties(TEST_CONFIG_FILE)
    val topic = props.getProperty("output.topic.name")

    val mockProducer = new MockProducer(true, new StringSerializer, new StringSerializer)
    val producerApp = new KafkaProducerApplication(mockProducer, topic)

    val msg = List("1-Hello", "2-World", "3-Apache", "4-Kafka", "noKeyHere")
    val expectedList = List(KeyValue.pair("1", "Hello"),
      KeyValue.pair("2", "World"),
      KeyValue.pair("3", "Apache"),
      KeyValue.pair("4", "Kafka"),
      KeyValue.pair(null, "noKeyHere"))

    // When
    msg.foreach(producerApp.produce)

    // Then
    val actualList = mockProducer.history.asScala.toList.map(toKeyValue)
    assert(actualList.equals(expectedList))

    producerApp.shutdown()
  }

  private def toKeyValue(producerRecord: ProducerRecord[String, String]): KeyValue[String, String] = {
    KeyValue.pair(producerRecord.key(), producerRecord.value())
  }

}
