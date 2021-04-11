package ru.sfedu.cv.utils

import org.apache.log4j.{Logger => L4JLogger}

trait Logger {
  val log: L4JLogger = L4JLogger.getLogger(this.getClass)
}
