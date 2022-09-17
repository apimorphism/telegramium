import sbt._

object Dependencies {

  object V {
    val monix            = "3.4.0"
    val catsCore         = "2.8.0"
    val catsEffect       = "3.3.14"
    val circe            = "0.14.2"
    val http4s           = "0.23.16"
    val blazeHttp4s      = "0.23.12"
    val slf4j            = "2.0.1"
    val logback          = "1.2.11"
    val scalatest        = "3.2.13"
    val testcontainers   = "0.40.10"
    val mockServerClient = "5.11.2"
    val log4cats         = "2.4.0"
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

  val logger = Seq(
    "org.slf4j"      % "slf4j-api"       % V.slf4j,
    "org.slf4j"      % "slf4j-simple"    % V.slf4j,
    "ch.qos.logback" % "logback-classic" % V.logback,
    "org.typelevel" %% "log4cats-slf4j"  % V.log4cats
  )

  val testcontainers = Seq(
    "com.dimafeng"   %% "testcontainers-scala-scalatest"  % V.testcontainers   % Test,
    "com.dimafeng"   %% "testcontainers-scala-mockserver" % V.testcontainers   % Test,
    "org.mock-server" % "mockserver-client-java"          % V.mockServerClient % Test
  )

  val common: Seq[ModuleID] = Seq(catsCore) ++ circe

  val telegramiumCore: Seq[ModuleID] = common

  val telegramiumHigh: Seq[ModuleID] = common ++ Seq(catsEffect, scalatest) ++ http4sServer ++
    http4sClient ++ testcontainers ++ logger

  val telegramiumExam: Seq[ModuleID] = common ++ logger ++ Seq(catsEffect) ++ http4sClient
}
