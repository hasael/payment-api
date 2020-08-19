package com.hasael.paymentapi.config

import cats.effect.{Blocker, ContextShift, Resource, Sync}
import com.hasael.paymentapi.config.Config.{DynamoDbConbfig, ServerConfig}
import com.typesafe.config.ConfigFactory
import pureconfig.ConfigSource
import pureconfig.module.catseffect.syntax._
import pureconfig.generic.auto.exportReader

case class Config(dynamoDb: DynamoDbConbfig, server: ServerConfig)

object Config {

  case class DynamoDbConbfig(table: String)

  case class ServerConfig(host: String, port: Int)

  def load[F[_] : Sync : ContextShift](configFile: String): Resource[F, Config] =
    Blocker[F].flatMap { blocker =>
      Resource.liftF(ConfigSource.fromConfig(ConfigFactory.load(configFile)).loadF[F, Config](blocker))
    }
}