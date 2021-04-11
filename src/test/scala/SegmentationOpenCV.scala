import org.opencv.imgcodecs.Imgcodecs
import org.scalatest.funsuite.AnyFunSuite
import ru.sfedu.cv.ImageAPI.ImageAPI
import ru.sfedu.cv.enums.PyramidOpetaionType
import ru.sfedu.cv.implicits.OptionTransforms._

class SegmentationOpenCV extends AnyFunSuite {
  test("Test fill flood") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/1.jpg",
      Imgcodecs.IMREAD_COLOR,
    )
    instanceImageAPI.fillFlood(
      pointCoords = (20, 20),
      fillColor = (0, 0, 255),
      rangeColor = (0, 0, 0) -> (0, 0, 0)
    )
    instanceImageAPI.showImage()
  }

  test("Test find contours") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/recognize_rect.png",
      Imgcodecs.IMREAD_COLOR,
    )

    instanceImageAPI.recognizeRects(
      rangeWidthHeight = (0, 0) -> (Int.MaxValue, Int.MaxValue),
      recognitionAccuracy = 99.0,
      needToShow = true
    )
  }

  test("Test pyramids down") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/cat_2.jpg",
      Imgcodecs.IMREAD_COLOR,
    )

    instanceImageAPI.pyramidTransform(3, PyramidOpetaionType.DOWN)
    instanceImageAPI.showImage()
  }

  test("Test pyramids up") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/cat_2.jpg",
      Imgcodecs.IMREAD_COLOR,
    )

    instanceImageAPI.pyramidTransform(3, PyramidOpetaionType.UP)
    instanceImageAPI.showImage()
  }
}
