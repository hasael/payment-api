package com.hasael.paymentapi.routes

import cats.effect.Sync
import cats.syntax.flatMap.toFlatMapOps
import cats.syntax.functor.toFunctorOps
import com.hasael.paymentapi.core.AuthorizationRequest
import com.hasael.paymentapi.core.PaymentResponse._
import com.hasael.paymentapi.ops.RequestOps._
import com.hasael.paymentapi.services.PaymentService
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object PaymentRoutes {
  def apply[F[_] : Sync](paymentService: PaymentService[F]): HttpRoutes[F] = {
    object dsl extends Http4sDsl[F];
    import dsl._

    HttpRoutes.of[F] {
      case req@POST -> Root / "authorize" =>
        req.extract[AuthorizationRequest] {
          data =>
            for {
              res <- paymentService.authorize(data)
              respo <- Ok(res)
            } yield respo
        }


    }
  }
}

