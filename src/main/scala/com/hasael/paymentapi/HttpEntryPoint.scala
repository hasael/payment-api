package com.hasael.paymentapi

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object HttpEntryPoint extends IOApp with AppBuilder {

  override def run(args: List[String]): IO[ExitCode] = {
    appResource[IO].use { app =>
      BlazeServerBuilder[IO](global)
        .bindHttp(8080, "localhost")
        .withHttpApp(app)
        .serve
        .compile
        .drain
        .as(ExitCode.Success)
    }
  }
}