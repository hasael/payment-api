package com.hasael.paymentapi

import cats.effect.IO
import com.hasael.paymentapi.lambda.LambdaHandler
import org.http4s.HttpApp

object LambdaEntryPoint extends LambdaHandler with AppBuilder {
  override def run: IO[HttpApp[IO]] =
    appResource[IO].use(app => IO(app))

}

