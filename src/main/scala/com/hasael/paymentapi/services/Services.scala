package com.hasael.paymentapi.services

import cats.effect.Sync
import com.hasael.paymentapi.repository.{DynamoClientProvider, DynamoDbRepository}

case class Services[F[_]](paymentService: PaymentService[F])

object Services{
  def load[F[_]:Sync] :Services[F] = new Services[F](PaymentService.impl[F](PspService.impl[F], DynamoDbRepository.impl[F](new DynamoClientProvider().client)))
}
