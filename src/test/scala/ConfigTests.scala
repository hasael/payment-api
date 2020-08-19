import org.scalatest.funsuite.AnyFunSuite
import cats.effect.IO
import com.hasael.paymentapi.config.Config

import scala.concurrent.ExecutionContext.global

class ConfigTests extends AnyFunSuite {
  implicit val cs = IO.contextShift(global)

  test("load configuration correctly")  {
    assert(Config.load[IO]("application.conf").use(IO(_)).attempt.unsafeRunSync().isRight)
  }
}
