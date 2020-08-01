
import Launch.{paymentService, routes}
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import _root_.routes.{PaymentRoutes, VersionRoutes}
import services.PaymentService
import cats.implicits._
import org.http4s.HttpApp
import org.http4s._
import cats.effect._
import org.http4s.server.middleware._

import scala.concurrent.ExecutionContext.Implicits.global

trait Routes {
  val paymentService = PaymentService.impl[IO]

  val routes = PaymentRoutes(paymentService) <+>
    VersionRoutes()
}

object Launch extends IOApp with Routes {


  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(routes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}

class LambdaEntryPoint extends LambdaHandler with Routes {

  def run: IO[HttpApp[IO]] = IO {
    routes.orNotFound
  }
}