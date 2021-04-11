import org.opencv.imgcodecs.Imgcodecs
import org.scalatest.funsuite.AnyFunSuite
import ru.sfedu.cv.ImageAPI.ImageAPI

class MutationOpenCV extends AnyFunSuite {
  test("Test reset 1 channel") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/color.jpg",
      Imgcodecs.IMREAD_COLOR
    )
    instanceImageAPI.mutatePixels((pxl, _) => pxl.updated(0, 0))
    instanceImageAPI.showImage()
  }

  test("Test reset 2 channel") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/color.jpg",
      Imgcodecs.IMREAD_COLOR
    )
    instanceImageAPI.mutatePixels((pxl, _) => pxl.updated(1, 0))
    instanceImageAPI.showImage()
  }

  test("Test reset 3 channel") {
    val instanceImageAPI = new ImageAPI(
      "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/color.jpg",
      Imgcodecs.IMREAD_COLOR
    )
    instanceImageAPI.mutatePixels((pxl, _) => pxl.updated(2, 0))
    instanceImageAPI.showImage()
  }
}
