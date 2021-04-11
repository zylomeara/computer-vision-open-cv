package ru.sfedu.cv.helpers

import org.opencv.core.{CvType, MatOfPoint, MatOfPoint2f, Point}
import org.opencv.imgproc.Imgproc

object SegmentationHelpers {
  type RangeSizeOfRect = ((Int, Int), (Int, Int))

  def distanceBetween(a: Point, b: Point) = Math.sqrt(Math.pow(b.y - a.y, 2) + Math.pow(b.x - a.x, 2));

  def getRectSides(contour: MatOfPoint) = {
    val contourVertices = contour.toArray
    val firstSide = distanceBetween(contourVertices(0), contourVertices(1))
    val secondSide = distanceBetween(contourVertices(1), contourVertices(2))
    val thirdSide = distanceBetween(contourVertices(2), contourVertices(3))
    val fourthSide = distanceBetween(contourVertices(3), contourVertices(0))

    (firstSide, secondSide, thirdSide, fourthSide)
  }

  def approximateContour(contour: MatOfPoint) = {
    val point2f = new MatOfPoint2f // матрица точек с плавающими 32-б числами
    val approxContour2f = new MatOfPoint2f
    val approxContour = new MatOfPoint
    contour.convertTo(point2f, CvType.CV_32FC2) // преобразование в 32-битную матрицу чисел типа float
    val arcLength = Imgproc.arcLength(point2f, true) // // вычисление длины контура (периметр)
    /**
     * контурная аппроксимация - это алгоритм для уменьшения количества точек на кривой (поиск аналогичной кривой с меньшим количеством точек)
     * https://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm
     *
     * epsilon - расстояние при которой точка отбрасывается в случае нахождения ближе
     */
    Imgproc.approxPolyDP(point2f, approxContour2f, 0.03 * arcLength, true)
    approxContour2f.convertTo(approxContour, CvType.CV_32S) // преобразование в 32-б целочисленный массив

    approxContour
  }

  def isRectFigure(contour: MatOfPoint, recognitionAccuracy: Double): Boolean = {
    require(
      recognitionAccuracy >= 0 && recognitionAccuracy <= 100,
      "Accuracy must be greater than zero and not more than 100"
    )
    if (contour.toArray.length != 4) {
      return false
    }

    val (firstSide, secondSide, thirdSide, fourthSide) = getRectSides(contour)
    val contourArea = Imgproc.contourArea(contour)
    val requiredArea = firstSide * secondSide

    val isEqualSides = (
      Math.abs(firstSide - thirdSide) <= firstSide * ((100d - recognitionAccuracy)/100d)
      ) && (
      Math.abs(secondSide - fourthSide) <= secondSide * ((100d - recognitionAccuracy)/100d)
      )

    val figureAreaIsRectArea = Math.abs(contourArea - requiredArea) <= requiredArea * ((100d - recognitionAccuracy)/100d)

    isEqualSides && figureAreaIsRectArea
  }

  def getRectWidthHeight(contour: MatOfPoint) = {
    val (firstSide, secondSide, _, _) = getRectSides(contour)

    val width = Math.max(firstSide, secondSide)
    val height = Math.min(firstSide, secondSide)

    (width, height)
  }

  def isRectCorrespondsToTheRange(contour: MatOfPoint, rangeSizeOfRect: RangeSizeOfRect) = {
    require(contour.toArray.length == 4)
    val (width, height) = getRectWidthHeight(contour)

    val rangeFrom = rangeSizeOfRect._1
    val rangeTo = rangeSizeOfRect._2

    val rangeFromWidth = rangeFrom._1
    val rangeFromHeight = rangeFrom._2

    val rangeToWidth = rangeTo._1
    val rangeToHeight = rangeTo._2

    rangeFromWidth < width && width < rangeToWidth && rangeFromHeight < height && height < rangeToHeight
  }
}
