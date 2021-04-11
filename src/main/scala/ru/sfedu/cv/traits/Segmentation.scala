package ru.sfedu.cv.traits

import org.opencv.core._
import org.opencv.imgproc.Imgproc
import org.opencv.photo.Photo
import ru.sfedu.cv.config.CVLibConfig.pathToDestinationImages
import ru.sfedu.cv.enums.PyramidOpetaionType
import ru.sfedu.cv.enums.PyramidOpetaionType.{DOWN, UP}
import ru.sfedu.cv.implicits.OptionTransforms._
import ru.sfedu.cv.utils.CVUtils
import ru.sfedu.cv.helpers.SegmentationHelpers._

import scala.util.{Random => Rnd}

// implicits
import scala.jdk.CollectionConverters._

trait Segmentation extends InitializeNativeCvLib {
  protected val srcImage: Mat
  protected val dstImage: Mat

  def fillFlood(
    pointCoords: (Int, Int) = (0, 0),
    fillColor: Option[(Int, Int, Int)] = None,
    rangeColor: ((Int, Int, Int), (Int, Int, Int)) = (0, 0, 0) -> (0, 0, 0)
  ): Unit = {
    val seedPoint = new Point(pointCoords._1, pointCoords._2)
    val newVal = fillColor match {
      case Some(colors) => new Scalar(colors._1, colors._2, colors._3)
      case None => new Scalar(Rnd.between(0, 255), Rnd.between(0, 255), Rnd.between(0, 255))
    }
    val rangeFrom = rangeColor._1
    val rangeTo = rangeColor._2
    val loDiff = new Scalar(rangeFrom._1, rangeFrom._2, rangeFrom._3)
    val upDiff = new Scalar(rangeTo._1, rangeTo._2, rangeTo._3)
    val mask = new Mat
    val rect = new Rect

    /**
     * FLOODFILL_FIXED_RANGE - loDiff и upDiff задают разницу между начальным и конечным пикселами
     * FLOODFILL_MASK_ONLY - результат будет записан в матрицу mask
     */
    Imgproc.floodFill(dstImage, mask, seedPoint, newVal, rect, loDiff, upDiff, Imgproc.FLOODFILL_FIXED_RANGE)
    val color = newVal.`val`.map(_.toInt).dropRight(1).mkString(",")
    log.debug(s"Image is filled in color ($color) with point coordinates $pointCoords with range color $rangeFrom -> $rangeTo")
  }

  def pyramidTransform(
    countIterates: Int,
    pyrOpType: PyramidOpetaionType.Value
  ) = {
    require(countIterates > 0)
    val iterator = 1 to countIterates

    pyrOpType match {
      case UP =>
        iterator.foreach(_ => Imgproc.pyrUp(
          dstImage,
          dstImage,
          new Size(dstImage.cols * 2, dstImage.rows * 2))
        )
        iterator.foreach(_ => Imgproc.pyrDown(
          dstImage,
          dstImage,
          new Size(dstImage.cols / 2, dstImage.rows / 2))
        )
      case DOWN =>
        iterator.foreach(_ => Imgproc.pyrDown(
          dstImage,
          dstImage,
          new Size(dstImage.cols / 2, dstImage.rows / 2))
        )
        iterator.foreach(_ => Imgproc.pyrUp(
          dstImage,
          dstImage,
          new Size(dstImage.cols * 2, dstImage.rows * 2))
        )
    }
    Imgproc.resize(dstImage.clone, dstImage, new Size(srcImage.cols, srcImage.rows))

    val info = "Image was transformed by " + (pyrOpType match {
      case UP => "PyramideUp"
      case DOWN => "PyramideDown"
    }) + s" with iterations: $countIterates"

    log.info(info)
  }

  def subtract = {
    Core.subtract(srcImage, dstImage.clone, dstImage)
    log.info("Received image fragment between source and destination image")
  }


  def recognizeRects(
    pathToSaveResults: Option[String] = None,
    rangeWidthHeight: RangeSizeOfRect = (0, 0) -> (Int.MaxValue, Int.MaxValue),
    recognitionAccuracy: Double = 0.0,
    needToShow: Boolean = false,
  ): Unit = {
    // 1
    // Преобразование в ч/б изображение
    val grayImage = new Mat();
    Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
    log.debug("Image converted to gray")

    // 2
    // Шумоподавление с учетом того, что на изображении белый гауссов шум
    val denoisingImage = new Mat();
    Photo.fastNlMeansDenoising(grayImage, denoisingImage);
    log.debug("Noise reduction image with gauss noise")

    // 3
    // Метод обработки контрастности с использованием гистограммы изображения
    // Работает посредством выравнивания гистограммы цветов.
    // Пример: https://www.opencv-srf.com/2018/02/histogram-equalization.html
    val histogramEqualizationImage = new Mat();
    Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);
    log.debug("Image processed by contrast using image histogram")

    // 4
    val morphologicalOpeningImage = new Mat();
    val kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
    Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage, Imgproc.MORPH_RECT, kernel);
    log.debug("Image converted using morphological opening")

    // 5
    // Взятие фрагмента
    val subtractImage = new Mat();
    Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);
    log.debug("Received subtract fragment")

    // 6
    // Классифицирует пиксели с помощью порогового значения. Если превышает - белый, иначе - черный
    val thresholdImage = new Mat();
    Imgproc.threshold(subtractImage, thresholdImage, 50, 255, Imgproc.THRESH_OTSU);
    thresholdImage.convertTo(thresholdImage, CvType.CV_8UC1);
    log.debug("Pixels are classified into black and white.")


    // 7
    // Дилатация
    val dilatedImage = new Mat();
    Imgproc.dilate(thresholdImage, dilatedImage, kernel)
    log.debug("Image was processed by dilation")

    // 8
    // Поиск контуров
    val contours = new java.util.ArrayList[MatOfPoint]();
    Imgproc.findContours(dilatedImage, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
    log.debug("Obtained external contours from the image")
    val revOrderedContours = contours.asScala.sortWith((a, b) => Imgproc.contourArea(a) > Imgproc.contourArea(b))
    val foundContours = for {
      contour <- revOrderedContours
      approxContour <- approximateContour(contour)
      if isRectFigure(approxContour, recognitionAccuracy)
      if isRectCorrespondsToTheRange(approxContour, rangeWidthHeight)
    } yield {
      val rect = Imgproc.boundingRect(approxContour) // Вычисляет ограничивающий прямоугольник для набора точек

      val lst = List[MatOfPoint](approxContour).asJava
      Imgproc.drawContours(srcImage, lst, -1, new Scalar(0, 255, 0), 3)

      val submat = srcImage.submat(rect) // часть матрицы

      pathToSaveResults match {
        case Some(path) => CVUtils.saveImage(submat, s"${path}result" + contour.hashCode + ".jpg")
        case None =>
      }
      if (needToShow)
        CVUtils.showImage(submat, "result" + contour.hashCode)

    }
    val countRectangles = foundContours.length
    log.info(s"Found $countRectangles rectangles")

    pathToSaveResults match {
      case Some(path) => CVUtils.saveImage(srcImage, s"${path}SRC with $countRectangles rectangles.jpg")
      case None =>
    }
    if (needToShow)
      CVUtils.showImage(srcImage, s"SRC with $countRectangles rectangles")
  }
}
