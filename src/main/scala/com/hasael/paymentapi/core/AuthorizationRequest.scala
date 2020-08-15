package com.hasael.paymentapi.core

import cats.data.NonEmptyList
import cats.effect.Sync
import io.circe._
import org.http4s.implicits._
import io.circe.generic.semiauto.deriveDecoder
import org.http4s.{DecodeFailure, DecodeResult, EntityDecoder, InvalidMessageBodyFailure, Media, MediaRange}
import org.http4s.circe._
import com.hasael.paymentapi.validation.Validations._

case class AuthorizationRequest(data: String, trxId: String, additional: Option[String])

object AuthorizationRequest {
  implicit val decoder: Decoder[AuthorizationRequest] = deriveDecoder[AuthorizationRequest]
    .validate(nonEmptyString("data"), "data should not be empty")
    .validate(minLengthString("trxId", 5), "trxid should have length of minimum 5")


  implicit def entityDecoder[F[_] : Sync]: EntityDecoder[F, AuthorizationRequest] =
  accumulatingJsonOf[F, AuthorizationRequest]

}