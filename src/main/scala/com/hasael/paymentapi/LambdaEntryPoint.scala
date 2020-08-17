package com.hasael.paymentapi

import cats.effect.{ExitCode, IO, IOApp}
import com.hasael.paymentapi.lambda.LambdaHandler
import org.http4s.HttpApp

import scala.concurrent.ExecutionContext.Implicits.global

object LambdaEntryPoint extends LambdaHandler with AppBuilder {

  override def run: IO[HttpApp[IO]] = IO {
    app[IO]
  }
}

