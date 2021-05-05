package com.gitHub.scalaKafkaSandbox.config

import com.gitHub.scalaKafkaSandbox.config.Configuration.TopicConf.TopicSpec
import com.typesafe.config.Config

import java.util.Properties
import scala.jdk.CollectionConverters._

object Configuration {
  case class ProducerConf(clientConfig: Config, topics: TopicConf)

  case class ConsumerConf(clientConfig: Config, topics: TopicConf)

  case class TopicConf(topicSpec: TopicSpec) {
    def all: Vector[TopicSpec] = Vector(topicSpec)
  }

  object TopicConf {
    case class TopicSpec(name: String, partitions: Int, replicationFactor: Short)
  }
}

trait Configuration {
  implicit class configMapperOps(config: Config) {

    def toMap: Map[String, AnyRef] = config
      .entrySet()
      .asScala
      .map(pair => (pair.getKey, config.getAnyRef(pair.getKey)))
      .toMap

    def toProperties: Properties = {
      val properties = new Properties()
      properties.putAll(config.toMap.asJava)
      properties
    }
  }
}
