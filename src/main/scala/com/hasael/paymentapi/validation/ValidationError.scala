package com.hasael.paymentapi.validation

import cats.Applicative
import cats.effect.Sync
import io.circe.generic.semiauto._
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe._

case class ValidationError(validationErrors: List[String])

object ValidationError {
  implicit val encoder = deriveEncoder[ValidationError]
  implicit val decoder = deriveDecoder[ValidationError]

  implicit def entityEncoderValidationError[F[_] : Applicative]: EntityEncoder[F, ValidationError] =
    jsonEncoderOf[F, ValidationError]

  implicit def entityDecoder[F[_] : Sync]: EntityDecoder[F, ValidationError] =
    accumulatingJsonOf[F, ValidationError]

}