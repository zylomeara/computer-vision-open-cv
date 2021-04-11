import org.opencv.imgcodecs.Imgcodecs
import org.scalatest.funsuite.AnyFunSuite
import ru.sfedu.cv.ImageAPI.ImageAPI
import ru.sfedu.cv.enums.MorphologyTransformsType

class MorphologyOpenCV extends AnyFunSuite {
  val imagePath = "/Users/tk_user/IdeaProjects/opencv_scala/src/main/resources/images/in/morph.png"

  test("Test blackhat morphology transform") {
    val instanceImageAPI = new ImageAPI(imagePath, Imgcodecs.IMREAD_GRAYSCALE)
    instanceImageAPI.morphologyTransform(MorphologyTransformsType.MORPH_BLACKHAT, 5)
    instanceImageAPI.showImage()
  }

  test("Test close morphology transform") {
    val instanceImageAPI = new ImageAPI(imagePath, Imgcodecs.IMREAD_GRAYSCALE)
    instanceImageAPI.morphologyTransform(MorphologyTransformsType.MORPH_CLOSE, 5)
    instanceImageAPI.showImage()
  }

  test("Test dilate morphology transform") {
    val instanceImageAPI = new ImageAPI(imagePath, Imgcodecs.IMREAD_GRAYSCALE)
    instanceImageAPI.morphologyTransform(MorphologyTransformsType.MORPH_DILATE, 5)
    instanceImageAPI.showImage()
  }

  test("Test erode morphology transform") {
    val instanceImageAPI = new ImageAPI(imagePath, Imgcodecs.IMREAD_GRAYSCALE)
    instanceImageAPI.morphologyTransform(MorphologyTransformsType.MORPH_ERODE, 5)
    instanceImageAPI.showImage()
  }

  test("Test gradient morphology transform") {
    val instanceImageAPI = new ImageAPI(imagePath, Imgcodecs.IMREAD_GRAYSCALE)
    instanceImageAPI.morphologyTransform(MorphologyTransformsType.MORPH_GRADIENT, 5)
    instanceImageAPI.showImage()
  }

  test("Test open morphology transform") {
    val instanceImageAPI = new ImageAPI(imagePath, Imgcodecs.IMREAD_GRAYSCALE)
    instanceImageAPI.morphologyTransform(MorphologyTransformsType.MORPH_OPEN, 5)
    instanceImageAPI.showImage()
  }
}
