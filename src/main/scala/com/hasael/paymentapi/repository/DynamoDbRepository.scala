package com.hasael.paymentapi.repository

import cats.effect.Sync
import com.hasael.paymentapi.core.{AuthorizationRequest, PaymentResponse}
import org.scanamo._
import org.scanamo.syntax._
import org.scanamo.generic.auto._
import cats.implicits._
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.hasael.paymentapi.repository.models.AuthorizationData

trait DynamoDbRepository[F[_]] {

  def save(authorizationRequest: AuthorizationRequest, response: PaymentResponse): F[Unit]
}

object DynamoDbRepository {
  def impl[F[_] : Sync](client: AmazonDynamoDBAsync): DynamoDbRepository[F] = new DynamoDbRepository[F] {
    override def save(authorizationRequest: AuthorizationRequest, response: PaymentResponse): F[Unit] = {
      val table = Table[AuthorizationData]("authorizationData")
      val data = AuthorizationData.from(authorizationRequest, response)
      Scanamo(client).exec(table.put(data)).pure[F]
    }
  }
}