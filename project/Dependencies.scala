import sbt._

object Dependencies {

  object V {
    val monix      = "3.1.0"
    val catsCore   = "2.1.1"
    val catsEffect = "2.1.3"
    val circe      = "0.13.0"
    val http4s     = "0.21.3"
    val slf4j      = "1.7.26"
    val logback    = "1.2.3"
    val uPickle    = "0.8.0"
  }

  val monix      = "io.monix" %% "monix" % V.monix
  val catsCore   = "org.typelevel" %% "cats-core" % V.catsCore
  val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect

  val circe = Seq(
    "io.circe" %% "circe-core"           % V.circe,
    "io.circe" %% "circe-parser"         % V.circe,
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-dsl"          % V.http4s,
    "org.http4s" %% "http4s-circe"        % V.http4s,
    "org.http4s" %% "http4s-blaze-server" % V.http4s,
    "org.http4s" %% "http4s-blaze-client" % V.http4s,
  )

  val logger = Seq(
    "org.slf4j"      % "slf4j-api"       % V.slf4j,
    "org.slf4j"      % "slf4j-simple"    % V.slf4j,
    "ch.qos.logback" % "logback-classic" % V.logback,
  )

  val uPickle = Seq(
    "com.lihaoyi" %% "upickle" % V.uPickle,
    "com.lihaoyi" %% "upack" % V.uPickle,
  )

  val common = Seq(
    catsCore,
    catsEffect,
  ) ++ circe ++ http4s ++ uPickle

  val telegramiumCore: Seq[ModuleID] = common
  val telegramiumHigh: Seq[ModuleID] = common
  val telegramiumExam: Seq[ModuleID] = common ++ logger ++ Seq(monix)
}
