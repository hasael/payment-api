package com.hasael.paymentapi.ops

import cats.effect.Sync
import cats.{Applicative, Monad}
import org.http4s._
import org.http4s.implicits._
import org.http4s.circe._
import cats.implicits._
import com.hasael.paymentapi.validation.ValidationError
import org.http4s.dsl._

object RequestOps {

  implicit class RichRequest[F[_] : Applicative](self: Request[F]) {
    def extract[A](f: A => F[Response[F]])(implicit m: Monad[F], decoder: EntityDecoder[F, A]): F[Response[F]] = {
      decoder
        .decode(self, strict = false)
        .fold(e => toResponse(e), f)
        .flatten
    }

    private def toResponse(decodeFailure: DecodeFailure): F[Response[F]] = {

      val validationErros = decodeFailure.cause match {
        case Some(d: DecodingFailures) => ValidationError(d.failures.map(_.getMessage()).toList)
        case Some(d) => ValidationError(List(d.getMessage))
        case None => ValidationError(List("Could not parse request"))
      }
      Response[F](Status.BadRequest).withEntity(validationErros).pure[F]
    }

  }

}
