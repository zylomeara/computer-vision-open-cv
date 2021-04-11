package ru.sfedu.cv

import java.io.File

import org.opencv.imgcodecs.Imgcodecs
import ru.sfedu.cv.ImageAPI.ImageAPI
import ru.sfedu.cv.traits.InitializeNativeCvLib

import scala.util.Try
import ru.sfedu.cv.config.CVLibConfig.{widthHeightOfRecognizeRectLab4, accuracyOfRecognizeRectLab4, imageTypeForRecognizeRectLab4, pathToImageForRecognizeRectLab4, colorForFillFloodLab4, countIteratesOfPyramidLab4, directionOfPyramidLab4, imageTypeForFillFloodLab4, imageTypeForFilterLab3, imageTypeForMorphologyLab3, imageTypeForPyramidLab4, imageTypeLab2, pathToDestinationImages, pathToImageForFillFloodLab4, pathToImageForFilterLab3, pathToImageForMorphologyLab3, pathToImageForPyramidLab4, pathToImageLab2, pointCoordinatesForFillFloodLab4, rangeColorForFillFloodLab4}
import ru.sfedu.cv.enums.{BlurType, ChannelsEnum, MorphologyTransformsType, PyramidOpetaionType}
import ru.sfedu.cv.utils.{FileUtils, Logger}

object Main extends Logger {
  def main(args: Array[String]): Unit = {
    for {
      arg <- args.distinct
      num = Try(arg.toInt).getOrElse(-1)
      if 1 to 4 contains num
    } {
      num match {
        case 1 => {
          log.info("Demonstrate lab 1")
          new InitializeNativeCvLib {}
          log.debug("The library was successfully initialized.")
        }
        case 2 => {
          log.info("Demonstrate lab 2")
          val dstDirPathOfLab2 = pathToDestinationImages + "2/"
          val directory = new File(dstDirPathOfLab2)
          if (!directory.exists)
            directory.mkdir()

          val (channelsCount, imageType) = imageTypeLab2.toLowerCase match {
            case "gray" => (1, Imgcodecs.IMREAD_GRAYSCALE)
            case "color" | _ => (3, Imgcodecs.IMREAD_COLOR)
          }
          val imageInstance = new ImageAPI(pathToImageLab2, imageType)

          for {
            channelIndex <- 0 until channelsCount
            channelNum = channelIndex + 1
          } {
            imageInstance.mutatePixels((pxl, _) => pxl.updated(channelIndex, 0))
            val imageName = FileUtils.getFileName(pathToImageLab2)
            val imageNameWithPostfix = FileUtils.appendPostfix(imageName, s"__(channel_reset_at_$channelNum)")
            imageInstance.saveImage(dstDirPathOfLab2 + imageNameWithPostfix)
            imageInstance.resetChanges
          }
        }
        case 3 => {
          log.info("Demonstrate lab 3")
          val dstDirPathOfLab3 = pathToDestinationImages + "3/"
          val directory = new File(dstDirPathOfLab3)
          if (!directory.exists)
            directory.mkdir()

          val runFilters = {
            val dstFilterDirPath = dstDirPathOfLab3 + "filter/"
            val filterDir = new File(dstFilterDirPath)
            if (!filterDir.exists)
              filterDir.mkdir()

            val imageType= imageTypeForFilterLab3.toLowerCase match {
              case "gray" => Imgcodecs.IMREAD_GRAYSCALE
              case "color" | _ => Imgcodecs.IMREAD_COLOR
            }
            val imageInstance = new ImageAPI(pathToImageForFilterLab3, imageType)
            val imageName = FileUtils.getFileName(pathToImageForFilterLab3)

            for{
              blurType <- BlurType.values
              kernelSize <- 3 to 15 by 2
              kernelName = s"${kernelSize}x$kernelSize"
            } {
              val pathOfDirectory = dstFilterDirPath + s"blur with kernel ($kernelName)/"
              val directory = new File(pathOfDirectory)
              if (!directory.exists)
                directory.mkdir()

              imageInstance.blur(blurType, kernelSize)
              val imageNameWithPostfix = FileUtils.appendPostfix(imageName, s"__(blur_by_${blurType}_with_kernel_($kernelName))")
              imageInstance.saveImage(pathOfDirectory + imageNameWithPostfix)
              imageInstance.resetChanges
            }
          }

          val runMorphologyTransforms = {
            val dstMorphologyDirPath = dstDirPathOfLab3 + "morphology/"
            val morphologyDir = new File(dstMorphologyDirPath)
            if (!morphologyDir.exists)
              morphologyDir.mkdir()

            val imageType= imageTypeForMorphologyLab3.toLowerCase match {
              case "gray" => Imgcodecs.IMREAD_GRAYSCALE
              case "color" | _ => Imgcodecs.IMREAD_COLOR
            }
            val imageInstance = new ImageAPI(pathToImageForMorphologyLab3, imageType)
            val imageName = FileUtils.getFileName(pathToImageForMorphologyLab3)

            for{
              morphType <- MorphologyTransformsType.values
              kernelSize <- 3 to 15 by 2
              kernelName = s"${kernelSize}x$kernelSize"
            } {
              val pathOfDirectory = dstMorphologyDirPath + s"morph transforms with kernel ($kernelName)/"
              val directory = new File(pathOfDirectory)
              if (!directory.exists)
                directory.mkdir()

              imageInstance.morphologyTransform(morphType, kernelSize)
              val imageNameWithPostfix = FileUtils.appendPostfix(imageName, s"__(morph_transform_by_${morphType}_with_kernel_($kernelName))")
              imageInstance.saveImage(pathOfDirectory + imageNameWithPostfix)
              imageInstance.resetChanges
            }
          }
        }
        case 4 => {
          log.info("Demonstrate lab 4")
          val dstDirPathOfLab4 = pathToDestinationImages + "4/"
          val directory = new File(dstDirPathOfLab4)
          if (!directory.exists)
            directory.mkdir()

          val runFillFlood = {
            val dstFillFloodDirPath = dstDirPathOfLab4 + "fillFlood/"
            val fillFloodDir = new File(dstFillFloodDirPath)
            if (!fillFloodDir.exists)
              fillFloodDir.mkdir()

            val imageType= imageTypeForFillFloodLab4.toLowerCase match {
              case "gray" => Imgcodecs.IMREAD_GRAYSCALE
              case "color" | _ => Imgcodecs.IMREAD_COLOR
            }
            val imageInstance = new ImageAPI(pathToImageForFillFloodLab4, imageType)
            val imageName = FileUtils.getFileName(pathToImageForFillFloodLab4)

            val pointCoords = {
              val Array(x, y) = pointCoordinatesForFillFloodLab4
                .replace(" ", "")
                .drop(1)
                .dropRight(1)
                .split(",")
                .map(_.toInt)
              (x, y)
            }

            val fillColor = colorForFillFloodLab4
              .map(pointCoordsString => {
                val Array(b, g, r) = pointCoordsString
                  .replace(" ", "")
                  .drop(1)
                  .dropRight(1)
                  .split(",")
                  .map(_.toInt)
                (b, g, r)
              })

            val rangeColor = {
              val Array(
              (startB, startG, startR),
              (endB, endG, endR)
              ) = rangeColorForFillFloodLab4
                .split("->")
                .map(i => {
                  val Array(b, g, r) = i
                    .replace(" ", "")
                    .dropRight(1)
                    .drop(1)
                    .split(",")
                    .map(_.toInt)
                  (b,g,r)
                })
              (startB, startG, startR) -> (endB, endG, endR)
            }

            imageInstance.fillFlood(pointCoords, fillColor, rangeColor)
            val fillColorTitle = colorForFillFloodLab4 match {
              case Some(color) => color
              case None => "auto generate"
            }
            val imageNameWithPostfix = FileUtils.appendPostfix(
              imageName,
              s"__fill_flood_(" +
                s"point_coords=$pointCoordinatesForFillFloodLab4 " +
                s"fillColor=$fillColorTitle " +
                s"rangeColor=$rangeColorForFillFloodLab4)")
            imageInstance.saveImage(dstFillFloodDirPath + imageNameWithPostfix)
          }

          val runPyramidTransform = {
            val dstPyramidDirPath = dstDirPathOfLab4 + "pyramid/"
            val pyramidDir = new File(dstPyramidDirPath)
            if (!pyramidDir.exists)
              pyramidDir.mkdir()

            val imageType= imageTypeForPyramidLab4.toLowerCase match {
              case "gray" => Imgcodecs.IMREAD_GRAYSCALE
              case "color" | _ => Imgcodecs.IMREAD_COLOR
            }
            val imageInstance = new ImageAPI(pathToImageForPyramidLab4, imageType)
            val imageName = FileUtils.getFileName(pathToImageForPyramidLab4)

            val countIterates = countIteratesOfPyramidLab4.toInt
            val pyrOpType = directionOfPyramidLab4.toLowerCase match {
              case "up" => PyramidOpetaionType.UP
              case "down" | _ => PyramidOpetaionType.DOWN
            }

            imageInstance.pyramidTransform(countIterates, pyrOpType)

            var imageNameWithPostfix = FileUtils.appendPostfix(imageName, s"__($pyrOpType pyramid transforms with $countIterates iterations)")
            imageInstance.saveImage(dstPyramidDirPath + imageNameWithPostfix)
            imageInstance.subtract
            imageNameWithPostfix = FileUtils.appendPostfix(imageName, s"__(subtract between before and after pyramid transforms)")
            imageInstance.saveImage(dstPyramidDirPath + imageNameWithPostfix)
          }

          val runRecognizeRect = {
            val dstRecognizeRectDirPath = dstDirPathOfLab4 + "recognizeRect/"
            val recognizeRectDir = new File(dstRecognizeRectDirPath)
            if (!recognizeRectDir.exists)
              recognizeRectDir.mkdir()

            val imageType= imageTypeForRecognizeRectLab4.toLowerCase match {
              case "gray" => Imgcodecs.IMREAD_GRAYSCALE
              case "color" | _ => Imgcodecs.IMREAD_COLOR
            }
            val imageInstance = new ImageAPI(pathToImageForRecognizeRectLab4, imageType)
            val recognitionAccuracy = accuracyOfRecognizeRectLab4
              .replace(",", ".")
              .toDouble

            val rangeWidthHeight = {
              val Array(startWidth, startHeight, endWidth, endHeight) = widthHeightOfRecognizeRectLab4
                .replace(" ", "")
                .split("->")
                .flatMap(_
                  .drop(1)
                  .dropRight(1)
                  .split(","))
                .map {
                  case str if str matches "(?i)max" => Int.MaxValue
                  case str => str.toInt
                }
              (startWidth, startHeight) -> (endWidth, endHeight)
            }

            imageInstance.recognizeRects(Some(dstRecognizeRectDirPath), rangeWidthHeight, recognitionAccuracy)
          }
        }
      }
    }
  }
}
/*
java \
-Dconfig.file=./enviroment.properties \
-Dlog4j.configuration=file:"./log4j.properties" \
-jar opencv_scala.jar \
1 2 3 4
 */
