import org.opencv.imgcodecs.Imgcodecs
import org.scalatest._
import ru.sfedu.cv.ImageAPI.ImageAPI
import ru.sfedu.cv.enums.BlurType

class BlurOpenCV extends FunSuite {

  test("The image should be blurred by AVERAGE") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/cat_2.jpg",
            Imgcodecs.IMREAD_COLOR,
    )
    instanceImageAPI.blur(BlurType.AVERAGE, 9)
    instanceImageAPI.showImage()
  }

  test("The image should be blurred by GAUSSIAN") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/cat_2.jpg",
            Imgcodecs.IMREAD_COLOR,
    )
    instanceImageAPI.blur(BlurType.GAUSSIAN, 9)
    instanceImageAPI.showImage()
  }

  test("The image should be blurred by MEDIAN") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/cat_2.jpg",
            Imgcodecs.IMREAD_COLOR,
    )
    instanceImageAPI.blur(BlurType.MEDIAN, 9)
    instanceImageAPI.showImage()
  }

  test("The image should be blurred by BILATERAL") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/cat_2.jpg",
            Imgcodecs.IMREAD_COLOR,
    )
    instanceImageAPI.blur(BlurType.BILATERAL, 9)
    instanceImageAPI.showImage()
  }
}