package ru.sfedu.cv.config

import com.typesafe.config.ConfigFactory

object ConfigInstance {
  val config = ConfigFactory.load()
}
