package com.hasael.paymentapi


import cats.effect.Sync
import com.hasael.paymentapi.routes.Routes
import com.hasael.paymentapi.services.Services
import org.http4s.HttpApp

trait AppBuilder {

  def app[F[_] : Sync]: HttpApp[F] = Routes.load(Services.load[F])
}
