package ru.sfedu.cv.ImageAPI

import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import ru.sfedu.cv.traits.{Filtration, InitializeNativeCvLib, Morphology, Mutation, Segmentation}
import ru.sfedu.cv.utils.{CVUtils, FileUtils, Logger}


class ImageAPI (protected val imagePath: String, colorType: Int)
  extends InitializeNativeCvLib
  with Logger
  with Morphology
  with Segmentation
  with Filtration
  with Mutation {

  protected val srcImage: Mat = Imgcodecs.imread(imagePath, colorType)
  protected val dstImage: Mat = srcImage.clone
  require(!dstImage.empty, s"File not exists in $imagePath")
  log.info(s"Image loaded: $imagePath")
  val imageName: String = FileUtils.getFileName(imagePath)
  val imageDirPath: String = FileUtils.getDirPath(imagePath)

  def showImage(title: String = ""): Unit = {
    CVUtils.showImage(dstImage, title)
  }

  def saveImage(imagePath: String): Unit = {
    CVUtils.saveImage(dstImage, imagePath)
  }

  def resetChanges = {
    val bufferSize = srcImage.cols * srcImage.rows * srcImage.channels
    val b = new Array[Byte](bufferSize)
    srcImage.get(0, 0, b)
    dstImage.put(0, 0, b)
    log.debug("Reset changes to source image")
  }
}
