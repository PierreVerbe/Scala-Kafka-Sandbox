package com.gitHub.scalaKafkaSandbox.basics

import ch.qos.logback.core.util.StatusPrinter
import ch.qos.logback.classic.LoggerContext
import org.slf4j.{Logger, LoggerFactory}

object SimpleLogback extends App {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  logger.debug("This is a debug message")
  logger.error("This is an error message")
  logger.info("This is an info message")
  logger.trace("This is a trace message")
  logger.warn("This is a warn message")

  // Print internal state
  private val loggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
  StatusPrinter.print(loggerContext)

}
