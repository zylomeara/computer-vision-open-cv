package ru.sfedu.cv.enums

object BlurType extends Enumeration {
  val AVERAGE = Value("average")
  val GAUSSIAN = Value("gaussian")
  val MEDIAN = Value("median")
  val BILATERAL = Value("bilateral")
}
