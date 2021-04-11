package ru.sfedu.cv.enums

import org.opencv.imgproc.Imgproc

object MorphologyTransformsType extends Enumeration {
  val MORPH_DILATE = Value(Imgproc.MORPH_DILATE)      // Расширяет светлые области и сужает темные
  val MORPH_ERODE = Value(Imgproc.MORPH_ERODE)        // Сужает светлые области и расширяет темные
  val MORPH_OPEN = Value(Imgproc.MORPH_OPEN)          // MORPH_OPEN = MORPH_DILATE( MORPH_ERODE(src) )
  val MORPH_CLOSE = Value(Imgproc.MORPH_CLOSE)        // MORPH_CLOSE = MORPH_ERODE( MORPH_DILATE(src) )
  val MORPH_GRADIENT = Value(Imgproc.MORPH_GRADIENT)  // MORPH_GRADIENT = MORPH_DILATE(src) - MORPH_ERODE(src)
  val MORPH_BLACKHAT = Value(Imgproc.MORPH_BLACKHAT)  // MORPH_BLACKHAT = MORPH_CLOSE(src) - src
}