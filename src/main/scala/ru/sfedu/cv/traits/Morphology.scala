package ru.sfedu.cv.traits

import org.opencv.core.{Mat, Size}
import org.opencv.imgproc.Imgproc
import ru.sfedu.cv.utils.Logger
import ru.sfedu.cv.enums.MorphologyTransformsType

trait Morphology extends InitializeNativeCvLib with Logger {
  protected val dstImage: Mat

  def morphologyTransform(morphologyType: MorphologyTransformsType.Value, kernelSize: Int): Unit = {
    require(kernelSize % 2 != 0, "Need an odd number")
    val kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(kernelSize, kernelSize))

    Imgproc.morphologyEx(dstImage.clone, dstImage, morphologyType.id, kernel)
    log.debug(s"Image was transformed by $morphologyType with kernel (${kernelSize}x$kernelSize)")
  }
}
