import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import routes.{PaymentRoutes, VersionRoutes}
import services.PaymentService
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global

object Launch extends IOApp {

  val paymentService = PaymentService.impl[IO]

  val routes = PaymentRoutes(paymentService) <+>
    VersionRoutes()

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
