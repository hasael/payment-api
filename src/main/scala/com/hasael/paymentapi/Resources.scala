package com.hasael.paymentapi

import cats.effect.{ContextShift, Resource, Sync}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.hasael.paymentapi.config.Config
import cats.syntax.applicative.catsSyntaxApplicativeId
import com.hasael.paymentapi.repository.DynamoClientProvider

case class Resources(config: Config, dynamoDbClient: AmazonDynamoDBAsync)

object Resources {
  def load[F[_] : Sync : ContextShift](configFile: String): Resource[F, Resources] = for {
    config <- Config.load(configFile)
    dynamoClient <- Resource.make(new DynamoClientProvider().client.pure[F])(_ => ().pure[F])
  } yield Resources(config, dynamoClient)
}