package com.hasael.paymentapi.services

import cats.Applicative
import com.hasael.paymentapi.core.{FailResponse, AuthorizationRequest, PaymentResponse, SuccessResponse}

trait PaymentService[F[_]] {
  def authorize(request: AuthorizationRequest): F[PaymentResponse]
}

object PaymentService {
  implicit def apply[F[_]](implicit ps: PaymentService[F]): PaymentService[F] = ps

  def impl[F[_] : Applicative]: PaymentService[F] = new PaymentService[F] {
    override def authorize(request: AuthorizationRequest): F[PaymentResponse] = Applicative[F].pure(FailResponse("failed payment!"))
  }

}
