package com.hasael.paymentapi

import cats.effect.IO
import org.http4s.HttpApp
import org.http4s.implicits._

class LambdaEntryPoint extends LambdaHandler with Routes with Services {

  def run: IO[HttpApp[IO]] = IO {
    routes(paymentService).orNotFound
  }
}
