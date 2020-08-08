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

import scala.concurrent.ExecutionContext.Implicits.global

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
