package com.hasael.paymentapi


import cats.effect.{ContextShift, Resource, Sync}
import com.hasael.paymentapi.routes.Routes
import com.hasael.paymentapi.services.Services
import org.http4s.HttpApp

trait AppBuilder {

  def appResource[F[_] : Sync : ContextShift]: Resource[F, HttpApp[F]] = {
    Resources.load("application.conf").map{
      resources => Routes.load[F](Services.load[F](resources))
    }
  }

}
