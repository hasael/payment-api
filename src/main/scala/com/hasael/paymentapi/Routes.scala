package com.hasael.paymentapi

import HttpEntryPoint.{routes}
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import com.hasael.paymentapi.routes.{PaymentRoutes, VersionRoutes}
import com.hasael.paymentapi.services.PaymentService
import cats.implicits._
import org.http4s.HttpApp
import org.http4s._
import cats.effect._
import org.http4s.server.middleware._

trait Routes {

  def routes[F[_] : Sync](paymentService: PaymentService[F]) = PaymentRoutes(paymentService) <+>
    VersionRoutes()
}

trait Services {
  val paymentService = PaymentService.impl[IO]

}
