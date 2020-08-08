package com.hasael.paymentapi.core

import cats.Applicative
import cats.effect.Sync
import io.circe._
import io.circe.syntax._
import io.circe.parser.decode
import org.http4s.{EntityEncoder, HttpRoutes}
import org.http4s.circe._
import org.http4s.implicits._
import org.http4s._
import cats.implicits._
import io.circe.Decoder.Result

case class AuthorizationRequest(data: String)

object AuthorizationRequest {
  implicit val decoder: Decoder[AuthorizationRequest] = new Decoder[AuthorizationRequest] {
    override def apply(c: HCursor): Result[AuthorizationRequest] = c.downField("data").as[String].map(AuthorizationRequest.apply)
  }

  implicit def entityDecoder[F[_] : Sync]: EntityDecoder[F, AuthorizationRequest] =
    jsonOf[F, AuthorizationRequest]
}