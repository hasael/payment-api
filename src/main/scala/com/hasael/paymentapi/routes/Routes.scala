package com.hasael.paymentapi.routes

import cats.effect.Sync
import cats.syntax.semigroupk.toSemigroupKOps
import com.hasael.paymentapi.services.Services
import org.http4s.HttpApp
import org.http4s.syntax.kleisli.http4sKleisliResponseSyntaxOptionT

object Routes{
  def load[F[_] : Sync](services: Services[F]): HttpApp[F] = (PaymentRoutes(services.paymentService) <+>
    VersionRoutes()).orNotFound
}