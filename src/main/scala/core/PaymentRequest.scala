package core

import cats.Applicative
import org.http4s.{EntityEncoder, HttpRoutes}

trait PaymentRequest {

}

case class AuthorizationRequest(data: String) extends PaymentRequest
