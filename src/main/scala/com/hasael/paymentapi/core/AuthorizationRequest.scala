package com.hasael.paymentapi.core

import cats.effect.Sync
import com.hasael.paymentapi.validation.Validations._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import org.http4s.EntityDecoder
import org.http4s.circe.accumulatingJsonOf

case class AuthorizationRequest(data: String, trxId: String, additional: Option[String])

object AuthorizationRequest {
  implicit val decoder: Decoder[AuthorizationRequest] = deriveDecoder[AuthorizationRequest]
    .validate(nonEmptyString("data"), "data should not be empty")
    .validate(minLengthString("trxId", 5), "trxid should have length of minimum 5")


  implicit def entityDecoder[F[_] : Sync]: EntityDecoder[F, AuthorizationRequest] =
  accumulatingJsonOf[F, AuthorizationRequest]

}