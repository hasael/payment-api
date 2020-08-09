package com.hasael.paymentapi

import cats.effect.{ExitCode, IO, IOApp}
import com.hasael.paymentapi.routes.{Routes, Services}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object HttpEntryPoint extends IOApp with Routes with Services {

  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(routes(paymentService).orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
