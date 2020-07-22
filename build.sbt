name := "payment-api"

version := "0.1"

scalaVersion := "2.13.1"
val http4sVersion = "0.21.5"
val catsEffectVersion = "2.1.3"
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.typelevel" %% "cats-effect" % catsEffectVersion
)
