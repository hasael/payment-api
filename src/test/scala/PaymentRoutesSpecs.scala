import cats.implicits._
import cats.effect.IO
import com.hasael.paymentapi.HttpEntryPoint.routes
import com.hasael.paymentapi.core.{AuthorizationRequest, FailResponse, PaymentResponse}
import com.hasael.paymentapi.services.PaymentService
import org.http4s._
import org.http4s.implicits._
import org.scalatest.funsuite.AnyFunSuite

class PaymentRoutesSpecs extends AnyFunSuite {

  private def httpApp = routes[IO](new PaymentService[IO] {
    override def authorize(request: AuthorizationRequest): IO[PaymentResponse] =  FailResponse("failed payment!").pure[IO]
  }).orNotFound

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