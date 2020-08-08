package com.hasael.paymentapi

import Launch.{paymentService, routes}
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

class LambdaEntryPoint extends LambdaHandler with Routes {

  def run: IO[HttpApp[IO]] = IO {
    routes.orNotFound
  }
}
