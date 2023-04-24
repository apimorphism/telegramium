import sbt._

object Dependencies {

  object V {
    val catsCore         = "2.9.0"
    val catsEffect       = "3.4.9"
    val circe            = "0.14.5"
    val http4s           = "0.23.18"
    val blazeHttp4s      = "0.23.14"
    val slf4j            = "2.0.7"
    val logback          = "1.3.7"
    val scalatest        = "3.2.15"
    val testcontainers   = "0.40.15"
    val mockServerClient = "5.14.0"
  }

  val catsCore   = "org.typelevel" %% "cats-core"   % V.catsCore
  val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect
  val scalatest  = "org.scalatest" %% "scalatest"   % V.scalatest % Test

  val circe = Seq(
    "io.circe" %% "circe-core"   % V.circe,
    "io.circe" %% "circe-parser" % V.circe
  )

  val http4sClient = Seq(
    "org.http4s" %% "http4s-circe"        % V.http4s,
    "org.http4s" %% "http4s-blaze-client" % V.blazeHttp4s
  )

  val http4sServer = Seq(
    "org.http4s" %% "http4s-dsl"          % V.http4s,
    "org.http4s" %% "http4s-blaze-server" % V.blazeHttp4s
  )

  val testcontainers = Seq(
    "com.dimafeng"   %% "testcontainers-scala-scalatest"  % V.testcontainers   % Test,
    "com.dimafeng"   %% "testcontainers-scala-mockserver" % V.testcontainers   % Test,
    "org.mock-server" % "mockserver-client-java"          % V.mockServerClient % Test
  )

  val common: Seq[ModuleID] = Seq(catsCore) ++ circe

  val telegramiumCore: Seq[ModuleID] = common

  val telegramiumHigh: Seq[ModuleID] = common ++ Seq(catsEffect, scalatest) ++ http4sServer ++
    http4sClient ++ testcontainers

  val telegramiumExam: Seq[ModuleID] = common ++ Seq(catsEffect) ++ http4sClient
}
