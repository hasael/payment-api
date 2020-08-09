package com.hasael.paymentapi.core

import cats.Applicative
import io.circe._
import org.http4s.EntityEncoder
import org.http4s.circe._

trait PaymentResponse {
 def response:String
}

case class FailResponse(response: String) extends PaymentResponse

case class SuccessResponse() extends PaymentResponse {
  override def response: String = "success"
}


object PaymentResponse {
  implicit val encoder: Encoder[PaymentResponse] = (r: PaymentResponse) => Json.obj(
    "response" -> Json.fromString(r.response)
  )

  implicit def entityEncoder[F[_]: Applicative]: EntityEncoder[F, PaymentResponse] =
    jsonEncoderOf[F, PaymentResponse]
}