ThisBuild / name := "Scala-Kafka-Sandbox"
ThisBuild / organization := "com.gitHub"
ThisBuild / version := "1.0"

ThisBuild / scalaVersion := "2.13.5"

/**
 * Properties Build
 */
lazy val pureConfigVersion = "0.15.0"
lazy val logbackVersion = "1.2.3"
lazy val kafkaVersion = "2.7.0"
lazy val slf4jVersion = "1.7.30"
lazy val scalaTestVersion = "3.0.8"
lazy val scalaCheckVersion = "1.14.3"

/**
 * SBT Library Management
 */
// Configuration
val pureConfig = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion

// Logging
val slf4j = "org.slf4j" % "slf4j-simple" % slf4jVersion
val logback = "ch.qos.logback" % "logback-classic" % logbackVersion

// Apache Kafka
val kafka = "org.apache.kafka" % "kafka-clients" % kafkaVersion
val kafkaStreams = "org.apache.kafka" %% "kafka-streams-scala" % kafkaVersion

// Tests
val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
val scalaCheck = "org.scalacheck" %% "scalacheck" % scalaCheckVersion

/**
 * SBT Settings
 */
lazy val kafkaSettings = Seq(libraryDependencies ++= Seq(kafka, kafkaStreams))
lazy val testPackageSettings = Seq(
  libraryDependencies += scalaTest % Test,
  libraryDependencies += scalaCheck % Test
)

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(kafka,
    kafkaStreams,
    slf4j),

  libraryDependencies += scalaTest % Test,
  libraryDependencies += scalaCheck % Test
)

lazy val rootSettings = kafkaSettings ++
  testPackageSettings ++
  Seq(
    libraryDependencies += pureConfig,
    libraryDependencies += logback,
  )

/**
 * Project and Sub-Project
 */
lazy val root = (project in file("."))
  .settings(rootSettings: _*)
  .settings(
    name := "Kafka Sandbox project"
  )

lazy val confluentSub = (project in file("Confluent"))
  .settings(commonSettings: _*)
  .settings(
    name := "Confluent tutorials sub project"
  )

lazy val flinkSub = (project in file("Flink-Sub"))
  .settings(commonSettings: _*)
  .settings(
    name := "Flink sub project"
  )

lazy val sparkSub = (project in file("Spark-Sub"))
  .settings(commonSettings: _*)
  .settings(
    name := "Spark sub project"
  )
