package ru.sfedu.cv.config

import ConfigInstance.config

import scala.util.Try

object CVLibConfig {
  val libPathMacOS = config.getString("cv-native-lib.path-mac-os")
  val libPathLinux = config.getString("cv-native-lib.path-linux")
  val libPathWindows = config.getString("cv-native-lib.path-windows")

  val pathToDestinationImages = config.getString("media.path_to_destination_images")

  val pathToImageLab2 = config.getString("lab-2.reset-channel-image-path")
  val imageTypeLab2 = config.getString("lab-2.reset-channel-image-type")

  val pathToImageForFilterLab3 = config.getString("lab-3.filter.image-path")
  val imageTypeForFilterLab3 = config.getString("lab-3.filter.image-type")

  val pathToImageForMorphologyLab3 = config.getString("lab-3.morphology.image-path")
  val imageTypeForMorphologyLab3 = config.getString("lab-3.morphology.image-type")

  val pathToImageForFillFloodLab4 = config.getString("lab-4.fill-flood.image-path")
  val imageTypeForFillFloodLab4 = config.getString("lab-4.fill-flood.image-type")
  val pointCoordinatesForFillFloodLab4 = config.getString("lab-4.fill-flood.point-coordinates")
  val colorForFillFloodLab4 = Try(config.getString("lab-4.fill-flood.fill-color")).toOption
  val rangeColorForFillFloodLab4 = config.getString("lab-4.fill-flood.range-color")

  val imageTypeForPyramidLab4 = config.getString("lab-4.pyramid.image-type")
  val pathToImageForPyramidLab4 = config.getString("lab-4.pyramid.image-path")
  val countIteratesOfPyramidLab4 = config.getString("lab-4.pyramid.count-iterates")
  val directionOfPyramidLab4 = config.getString("lab-4.pyramid.pyramid-direction")

  val pathToImageForRecognizeRectLab4 = config.getString("lab-4.recognize-rect.image-path")
  val imageTypeForRecognizeRectLab4 = config.getString("lab-4.recognize-rect.image-type")
  val accuracyOfRecognizeRectLab4 = config.getString("lab-4.recognize-rect.recognition-accuracy")
  val widthHeightOfRecognizeRectLab4 = config.getString("lab-4.recognize-rect.range-width-height-rect")

}
