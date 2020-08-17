package com.hasael.paymentapi.services

import cats.effect.Sync
import com.hasael.paymentapi.Resources
import com.hasael.paymentapi.repository.DynamoDbRepository

case class Services[F[_]](paymentService: PaymentService[F])

object Services {
  def load[F[_] : Sync](resources: Resources): Services[F] = new Services[F](PaymentService.impl[F](PspService.impl[F], DynamoDbRepository.impl[F](resources.dynamoDbClient, resources.config.dynamoDb.table)))
}
