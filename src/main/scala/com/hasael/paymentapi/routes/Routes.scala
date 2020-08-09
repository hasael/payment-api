package com.hasael.paymentapi.routes

import cats.effect.{IO, _}
import cats.implicits._
import com.hasael.paymentapi.services.PaymentService

trait Routes {

  def routes[F[_] : Sync](paymentService: PaymentService[F]) = PaymentRoutes(paymentService) <+>
    VersionRoutes()
}

trait Services {
  val paymentService = PaymentService.impl[IO]

}
