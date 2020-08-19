package com.hasael.paymentapi.repository

import cats.effect.Sync
import cats.syntax.applicative.catsSyntaxApplicativeId
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.hasael.paymentapi.core.{AuthorizationRequest, PaymentResponse}
import com.hasael.paymentapi.repository.models.AuthorizationData
import org.scanamo.generic.auto._
import org.scanamo.{Scanamo, Table}

trait DynamoDbRepository[F[_]] {

  def save(authorizationRequest: AuthorizationRequest, response: PaymentResponse): F[Unit]
}

object DynamoDbRepository {
  def impl[F[_] : Sync](client: AmazonDynamoDBAsync, tableName: String): DynamoDbRepository[F] = new DynamoDbRepository[F] {
    override def save(authorizationRequest: AuthorizationRequest, response: PaymentResponse): F[Unit] = {
      val table = Table[AuthorizationData](tableName)
      val data = AuthorizationData.from(authorizationRequest, response)
      Scanamo(client).exec(table.put(data)).pure[F]
    }
  }
}