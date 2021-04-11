package ru.sfedu.cv.traits

import org.opencv.core.{Mat, Size}
import org.opencv.imgproc.Imgproc
import ru.sfedu.cv.enums.BlurType
import ru.sfedu.cv.enums.BlurType.{AVERAGE, BILATERAL, GAUSSIAN, MEDIAN}
import ru.sfedu.cv.utils.Logger

trait Filtration extends Logger {
  protected val dstImage: Mat

  def blur(`type`: BlurType.Value, kernelSize: Int): Unit = {
    require(kernelSize % 2 != 0, "Need an odd number")

    `type` match {
      case AVERAGE =>
        Imgproc.blur(dstImage.clone, dstImage, new Size(kernelSize, kernelSize))

      case GAUSSIAN =>
        Imgproc.GaussianBlur(dstImage.clone, dstImage, new Size(kernelSize, kernelSize), 0)

      case MEDIAN =>
        Imgproc.medianBlur(dstImage.clone, dstImage, kernelSize)

      case BILATERAL =>
        Imgproc.bilateralFilter(dstImage.clone, dstImage, -1, kernelSize, kernelSize)
    }
    log.info(s"Image blurred by ${`type`} with radius: $kernelSize")
  }
}
