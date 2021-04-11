package ru.sfedu.cv.utils

object FileUtils {
  def getFileName(absolutePath: String): String =
    absolutePath.split("[\\\\/]").last

  def getDirPath(absolutePath: String): String =
    absolutePath.dropRight(getFileName(absolutePath).length)

  def appendPostfix(fileNameWithExt: String, postfix: String): String = {
    fileNameWithExt
      .split("\\.")
      .zipWithIndex
      .map{ case (partOfName, index) => {
        if (index == 0)
          partOfName + postfix
        else
          partOfName
      } }
      .mkString(".")
  }
}
