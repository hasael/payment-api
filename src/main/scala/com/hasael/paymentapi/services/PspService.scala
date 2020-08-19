package com.hasael.paymentapi.services

import cats.Applicative
import com.hasael.paymentapi.core.{PspRequest, PspResponse}


trait PspService[F[_]] {
  def authorize(pspRequest: PspRequest): F[PspResponse]
}

object PspService {
  def impl[F[_] : Applicative]: PspService[F] = new PspService[F] {
    override def authorize(pspRequest: PspRequest): F[PspResponse] = Applicative[F].pure(PspResponse("2412", "failed payment!"))
  }
}
