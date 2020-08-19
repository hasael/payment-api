package com.hasael.paymentapi.ops

import cats.syntax.flatMap.catsSyntaxFlatten
import cats.{Applicative, Monad}
import com.hasael.paymentapi.validation.ValidationError
import org.http4s.circe.DecodingFailures
import org.http4s.{DecodeFailure, EntityDecoder, Request, Response, Status}


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
      Applicative[F].pure(Response[F](Status.BadRequest).withEntity(validationErros))
    }

  }

}
