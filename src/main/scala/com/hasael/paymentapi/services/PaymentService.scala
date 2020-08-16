package com.hasael.paymentapi.services

import cats.implicits._
import cats.effect.Sync
import com.hasael.paymentapi.core.{AuthorizationRequest, FailResponse, PaymentResponse, PspRequest, SuccessResponse}
import com.hasael.paymentapi.repository.DynamoDbRepository

trait PaymentService[F[_]] {
  def authorize(request: AuthorizationRequest): F[PaymentResponse]
}

object PaymentService {
  implicit def apply[F[_]](implicit ps: PaymentService[F]): PaymentService[F] = ps

  def impl[F[_] : Sync](pspService: PspService[F], repository: DynamoDbRepository[F]): PaymentService[F] = new PaymentService[F] {
    override def authorize(request: AuthorizationRequest): F[PaymentResponse] =
      for {
        pspResponse <- pspService.authorize(PspRequest())
        response <- pspResponse.toAuthorizationResponse().pure[F]
        _ <- repository.save(request, response)
      } yield response
  }

}
