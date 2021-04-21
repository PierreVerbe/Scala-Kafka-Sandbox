package com.gitHub.scalaKafkaSandbox.consumerApp

import com.gitHub.scalaKafkaSandbox.tool.KafkaTool.loadProperties
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
    val recordsHandler = new FileWritingRecordsHandler()
    val props = loadProperties(TEST_CONFIG_FILE)
    val topic = props.getProperty("input.topic.name")
    val topicPartition = new TopicPartition(topic, 0)
    val mockConsumer = new MockConsumer[String, String](OffsetResetStrategy.EARLIEST)

    val consumerApplication = new KafkaConsumerApplication(mockConsumer, recordsHandler)

    val expectedList = List(KeyValue.pair("1", "Hello"),
      KeyValue.pair("2", "World"),
      KeyValue.pair("3", "Apache"),
      KeyValue.pair("4", "Kafka"),
      KeyValue.pair(null, "noKeyHere"))

    // When
    new Thread(() => consumerApplication.runConsume(props)).start()
    Thread.sleep(500)
    addTopicPartitionsAssignmentAndAddConsumerRecords(topic, mockConsumer, topicPartition)
    Thread.sleep(500)
    consumerApplication.shutdown()

    // Then
    val actualRecords = recordsHandler.listBufferRecords
    assert(actualRecords.length == 5)
    assert(actualRecords.equals(expectedList))
  }

  private def addTopicPartitionsAssignmentAndAddConsumerRecords(topic: String, mockConsumer: MockConsumer[String, String], topicPartition: TopicPartition): Unit = {
    val beginningOffsets = new util.HashMap[TopicPartition, java.lang.Long]()
    beginningOffsets.put(topicPartition, 0L)
    mockConsumer.rebalance(Collections.singletonList(topicPartition))
    mockConsumer.updateBeginningOffsets(beginningOffsets)
    addConsumerRecords(mockConsumer, topic)
  }

  private def addConsumerRecords(mockConsumer: MockConsumer[String, String], topic: String): Unit = {
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 0, "1", "Hello"))
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 1, "2", "World"))
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 2, "3", "Apache"))
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 3, "4", "Kafka"))
    mockConsumer.addRecord(new ConsumerRecord[String, String](topic, 0, 4, null, "noKeyHere"))
  }

}
