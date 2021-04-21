import sbt._

object Dependencies {

  object V {
    val monix = "3.3.0"
    val catsCore = "2.6.0"
    val catsEffect = "3.1.0"
    val circe = "0.13.0"
    val http4s = "0.21.22"
    val slf4j = "1.7.30"
    val logback = "1.2.3"
    val uPickle = "0.8.0"
    val scalatest = "3.2.8"
    val testcontainers = "0.39.3"
    val mockServerClient = "5.10.0"
    val scalamock = "5.1.0"
    val log4cats = "1.2.2"
  }

  val monix = "io.monix" %% "monix" % V.monix
  val catsCore = "org.typelevel" %% "cats-core" % V.catsCore
  val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect
  val scalatest = "org.scalatest" %% "scalatest" % V.scalatest % Test
  val scalamock = "org.scalamock" %% "scalamock" % V.scalamock % Test

  val circe = Seq(
    "io.circe" %% "circe-core"           % V.circe,
    "io.circe" %% "circe-parser"         % V.circe
  )

  val http4sClient = Seq(
    "org.http4s" %% "http4s-circe"        % V.http4s,
    "org.http4s" %% "http4s-blaze-client" % V.http4s
  )

  val http4sServer = Seq(
    "org.http4s" %% "http4s-dsl"          % V.http4s,
    "org.http4s" %% "http4s-blaze-server" % V.http4s
  )

  val logger = Seq(
    "org.slf4j"      % "slf4j-api"       % V.slf4j,
    "org.slf4j"      % "slf4j-simple"    % V.slf4j,
    "ch.qos.logback" % "logback-classic" % V.logback,
    "org.typelevel" %% "log4cats-slf4j" % V.log4cats
  )

  val uPickle = Seq(
    "com.lihaoyi" %% "upickle" % V.uPickle,
    "com.lihaoyi" %% "upack" % V.uPickle
  )

  val testcontainers = Seq(
    "com.dimafeng" %% "testcontainers-scala-scalatest" % V.testcontainers % Test,
    "com.dimafeng" %% "testcontainers-scala-mockserver" % V.testcontainers % Test,
    "org.mock-server" % "mockserver-client-java" % V.mockServerClient % Test
  )

  val common: Seq[ModuleID] = Seq(catsCore) ++ circe ++ uPickle

  val telegramiumCore: Seq[ModuleID] = common
  val telegramiumHigh: Seq[ModuleID] = common ++ Seq(catsEffect, monix % Test, scalatest, scalamock) ++ http4sServer ++
    http4sClient ++ testcontainers ++ logger
  val telegramiumExam: Seq[ModuleID] = common ++ logger ++ Seq(catsEffect, monix) ++ http4sClient
}
