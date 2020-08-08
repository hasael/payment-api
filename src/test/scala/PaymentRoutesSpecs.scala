import cats.effect.IO
import com.hasael.paymentapi.Launch.routes
import com.hasael.paymentapi.Routes
import org.scalatest.funsuite.AnyFunSuite
import org.http4s._
import org.http4s.implicits._

class PaymentRoutesSpecs extends AnyFunSuite {

  private def httpApp = routes.orNotFound

  test("Version routes should return 200") {
    val req = Request[IO]().withMethod(Method.GET)
      .withUri(uri"/version")
    val resp = httpApp(req).unsafeRunSync()
    assert(resp.status.code == 200)
  }

  test("Authorization routes return value") {
    val req = Request[IO]().withMethod(Method.POST)
      .withUri(uri"/authorize")
      .withEntity("{\n    \"data\":\"my data\"\n}")
    val resp = httpApp(req).unsafeRunSync()
    assert(resp.status.code == 200)
    assert(resp.as[String].unsafeRunSync() == "{\"response\":\"failed payment!\"}")
  }

}
