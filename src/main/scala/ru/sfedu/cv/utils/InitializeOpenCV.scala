package ru.sfedu.cv.utils

object InitializeOpenCV {
  var isLoaded = false
  def init(path: String) = {
    if (!isLoaded) {
      System.load(path)
      isLoaded = true
    }
  }
}
