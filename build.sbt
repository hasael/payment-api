
name := "payment-api-lambda"

version := "0.1"
scalaVersion := "2.13.1"
assemblyJarName in assembly := "payment-api-lambda.jar"

enablePlugins(SbtProguard)
proguardOptions in Proguard ++= Seq("-dontnote", "-dontwarn", "-ignorewarnings")
proguardOptions in Proguard += ProguardOptions.keepMain("Launch")

val http4sVersion = "0.21.7"
val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.scalatest" %% "scalatest" % "3.2.0" % "test"
)

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
