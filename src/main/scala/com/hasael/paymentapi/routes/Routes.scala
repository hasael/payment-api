package com.hasael.paymentapi.routes

import cats.effect._
import cats.implicits._
import com.hasael.paymentapi.services.Services
import org.http4s._
import org.http4s.syntax._
import org.http4s.implicits._

object Routes{
  def load[F[_] : Sync](services: Services[F]): HttpApp[F] = (PaymentRoutes(services.paymentService) <+>
    VersionRoutes()).orNotFound
}