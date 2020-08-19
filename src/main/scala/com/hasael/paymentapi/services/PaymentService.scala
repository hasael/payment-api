package com.hasael.paymentapi.services

import cats.effect.Sync
import cats.syntax.flatMap.toFlatMapOps
import cats.syntax.functor.toFunctorOps
import com.hasael.paymentapi.core.{AuthorizationRequest, PaymentResponse, PspRequest}
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
        response <- Sync[F].pure(pspResponse.toAuthorizationResponse())
        _ <- repository.save(request, response)
      } yield response
  }

}
