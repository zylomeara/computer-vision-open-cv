package ru.sfedu.cv.implicits

import scala.reflect.ClassTag
import scala.util.Random

object ListTransforms {
  implicit class List2ShuffleList[T: ClassTag](lst: Array[T]) {
    def shuffle: Array[T] = Random.shuffle(lst).toArray
  }

  implicit class When[A](a: A) {
    def when(f: A => Boolean)(g: A => A): A =
      if (f(a)) g(a) else a
  }

  implicit class WhenElse[A](a: A) {
    def whenElse(f: A => Boolean)(g: A => A, h: A => A): A =
      if (f(a)) g(a) else h(a)
  }
}
