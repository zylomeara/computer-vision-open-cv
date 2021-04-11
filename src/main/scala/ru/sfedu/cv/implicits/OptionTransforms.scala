package ru.sfedu.cv.implicits

object OptionTransforms {
  implicit def Value2Option[T](value: T): Option[T] = Option(value)

  implicit class Optionable[T <: Any](value: T) {
    def toOption: Option[T] = Option(value)
  }
}
