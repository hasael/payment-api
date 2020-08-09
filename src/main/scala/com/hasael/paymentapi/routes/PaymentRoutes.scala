package com.hasael.paymentapi.routes

import cats._
import cats.implicits._
import cats.effect._
import com.hasael.paymentapi.core.{AuthorizationRequest, FailResponse, PaymentResponse, SuccessResponse}
import org.http4s.{DecodeFailure, HttpRoutes}
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
    object dsl extends Http4sDsl[F];
    import dsl._

    HttpRoutes.of[F] {
      case req@POST -> Root / "authorize" =>
        for {
          data <- req.attemptAs[AuthorizationRequest].value

          response <- {
            data match {
              case Left(f) => BadRequest(f.cause.map(_.getMessage).getOrElse(f.getLocalizedMessage))
              case Right(value) => for {
                res <- paymentService.authorize(value)
                respo <- Ok(res)
              } yield respo
            }
          }
        } yield response

    }
  }
}

