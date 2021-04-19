package com.gitHub.scalaKafkaSandbox.consumerApp

import com.gitHub.scalaKafkaSandbox.toolKafka.loadProperties
import org.apache.kafka.clients.consumer.{ConsumerRecord, MockConsumer, OffsetResetStrategy}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.streams.KeyValue
import org.scalatest.FunSuite

import java.util
import java.util.Collections

class KafkaConsumerApplicationTest extends FunSuite {

  final val TEST_CONFIG_FILE = "Confluent/src/test/resources/configuration/test_consumer.properties"

  test("Testing KafkaConsumerApplication") {
    // Given
    val props = loadProperties(TEST_CONFIG_FILE)
    val topic = props.getProperty("input.topic.name")

    val topicPartition = new TopicPartition(topic, 0)
    val mockConsumer = new MockConsumer[String, String](OffsetResetStrategy.EARLIEST)

    val consumerApplication = new KafkaConsumerApplication(mockConsumer)

    val expectedList = List(KeyValue.pair("1", "Hello"),
      KeyValue.pair("2", "World"),
      KeyValue.pair("3", "Apache"),
      KeyValue.pair("4", "Kafka"),
      KeyValue.pair(null, "noKeyHere"))

    // When

    // Then
    /*
    val actualList = mockProducer.history.asScala.toList.map(toKeyValue)
    assert(actualList.size == 5)
    assert(actualList.equals(expectedList))

    producerApp.shutdown()

     */

  }


/*
  private def addTopicPartitionsAssignmentAndAddConsumerRecords(topic: String, mockConsumer: MockConsumer[String, String], topicPartition: TopicPartition): Unit = {
    val beginningOffsets = new util.HashMap[TopicPartition, Long]()
    beginningOffsets.put(topicPartition, 0L)
    mockConsumer.rebalance(Collections.singletonList(topicPartition))
    mockConsumer.updateBeginningOffsets(beginningOffsets)
    addConsumerRecords(mockConsumer, topic)
  }

  private def addConsumerRecords(mockConsumer: MockConsumer[String, String], topic: String): Unit = {
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 0, null, "foo"))
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 1, null, "bar"))
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 2, null, "baz"))
  }

 */

}
