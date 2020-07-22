package services

import cats.Applicative
import core.{FailResponse, PaymentRequest, PaymentResponse, SuccessResponse}

trait PaymentService[F[_]] {
  def authorize(request: PaymentRequest): F[PaymentResponse]
}

object PaymentService {
  implicit def apply[F[_]](implicit ps: PaymentService[F]): PaymentService[F] = ps

  def impl[F[_] : Applicative]: PaymentService[F] = new PaymentService[F] {
    override def authorize(request: PaymentRequest): F[PaymentResponse] = Applicative[F].pure(FailResponse("failed payment!"))
  }

}
