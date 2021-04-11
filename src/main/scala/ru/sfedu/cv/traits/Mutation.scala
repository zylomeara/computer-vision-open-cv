package ru.sfedu.cv.traits

import org.opencv.core.Mat
import ru.sfedu.cv.utils.Logger

// implicicts
import ru.sfedu.cv.implicits.ListTransforms.{List2ShuffleList, When}

trait Mutation extends Logger {
  protected val dstImage: Mat

  def mutatePixels(
    fn: (Array[Byte], Int) => Array[Byte],
    sortFN: (Array[Byte], Array[Byte]) => Boolean = (_, _) => false,
    needToShuffle: Boolean = false
  ): Unit = {
    val totalBytes = (dstImage.total * dstImage.elemSize).toInt
    var buffer = new Array[Byte](totalBytes)
    dstImage.get(0, 0, buffer)

    buffer = buffer
      .grouped(dstImage.channels)
      .toArray
      .when(_ => needToShuffle) (_.shuffle)
      .sortWith(sortFN)
      .zipWithIndex
      .flatMap { case (lst, index) => fn(lst, index) }

    dstImage.put(0, 0, buffer)
    log.info(s"Pixels changes by custom function")
  }
}
