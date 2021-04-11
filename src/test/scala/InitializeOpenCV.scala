import org.scalatest._
import ru.sfedu.cv.traits.InitializeNativeCvLib

class InitializeOpenCV extends FunSuite {

  test("The image should instatiate") {
    new InitializeNativeCvLib {}
  }
}