package routes

import cats.effect.Sync
import core.{AuthorizationRequest, FailResponse, SuccessResponse}
import org.http4s.HttpRoutes
import services.PaymentService
import org.http4s.dsl._
import org.http4s.dsl.io._
import org.http4s.implicits._
import cats.implicits._
import core.PaymentResponse._

object PaymentRoutes {
  def apply[F[_] : Sync](paymentService: PaymentService[F]): HttpRoutes[F] = {
    object dsl extends Http4sDsl[F]; import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "authorize" / data =>
        for {
          response <- paymentService.authorize(AuthorizationRequest(data))
          result <- Ok(response)
        } yield result

    }
  }
}
