package com.gitHub.scalaKafkaSandbox.tool

import java.io.{FileInputStream, IOException}
import java.util.Properties

object KafkaTool {

  @throws[IOException]
  def loadProperties(fileName: String): Properties = {
    val envProps = new Properties()
    val input = new FileInputStream(fileName)
    envProps.load(input)
    input.close()
    envProps
  }

}
