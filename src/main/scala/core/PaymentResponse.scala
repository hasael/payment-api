package core

import cats.Applicative
import org.http4s.{Entity, EntityEncoder, Headers}

trait PaymentResponse {

}

case class FailResponse(message: String) extends PaymentResponse

case class SuccessResponse() extends PaymentResponse


object PaymentResponse {
  implicit def entityEncoder[F[_] : Applicative]: EntityEncoder[F, PaymentResponse] =
    new EntityEncoder[F, PaymentResponse] {
      override def toEntity(a: PaymentResponse): Entity[F] = a match {
        case FailResponse(message) => Entity(fs2.Stream.emits(message.toCharArray.map(c => c.toByte)))
        case SuccessResponse() => Entity.empty
      }

      override def headers: Headers = Headers.empty
    }
}