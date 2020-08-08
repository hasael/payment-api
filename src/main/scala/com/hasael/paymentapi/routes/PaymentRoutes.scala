package com.hasael.paymentapi.routes

import cats.effect.Sync
import com.hasael.paymentapi.core.{AuthorizationRequest, FailResponse, PaymentResponse, SuccessResponse}
import org.http4s.HttpRoutes
import com.hasael.paymentapi.services.PaymentService
import org.http4s.dsl._
import org.http4s.dsl.io._
import org.http4s.implicits._
import cats.implicits._
import com.hasael.paymentapi.core.PaymentResponse._
import org.http4s.circe._
import org.http4s.circe.CirceEntityDecoder._


object PaymentRoutes {
  def apply[F[_] : Sync](paymentService: PaymentService[F]): HttpRoutes[F] = {
    object dsl extends Http4sDsl[F]; import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "authorize" / data =>
        for {
          response <- paymentService.authorize(AuthorizationRequest(data))
          result <- Ok(response)
        } yield result
      case req @ POST -> Root / "authorize"  =>
        for {
          data <- req.as[AuthorizationRequest]
          response <- paymentService.authorize(data)
          result <- Ok(response)
        } yield result

    }
  }
}

