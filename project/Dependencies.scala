import sbt._

object Dependencies {

  object V {
    val catsCore           = "2.13.0"
    val catsEffect         = "3.7.1"
    val circe              = "0.14.17"
    val http4s             = "0.23.38"
    val blazeHttp4s        = "0.23.18"
    val slf4j              = "2.0.7"
    val logback            = "1.3.7"
    val scalatest          = "3.2.20"
    val scalatestplus      = "3.2.20.0"
    val testcontainers     = "0.44.1"
    val mockServerClient   = "5.14.0"
    val zio                = "2.0.5"
    val zioInteropRS       = "2.0.2"
    val fs2ReactiveStreams = "3.14.0"
  }

  val catsCore      = "org.typelevel"     %% "cats-core"       % V.catsCore
  val catsEffect    = "org.typelevel"     %% "cats-effect"     % V.catsEffect
  val scalatest     = "org.scalatest"     %% "scalatest"       % V.scalatest     % Test
  val scalatestplus = "org.scalatestplus" %% "scalacheck-1-19" % V.scalatestplus % Test

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

  // Test-only: exercises how a ZIO user bridges a ZStream into an fs2.Stream (via the Reactive
  // Streams protocol) when handing a file to the library. Pinned to the versions
  // zio-interop-reactivestreams 2.0.2 was built against, to avoid any binary-compat surprises.
  val zioStreamsInterop = Seq(
    "dev.zio" %% "zio-streams"                 % V.zio                % Test,
    "dev.zio" %% "zio-interop-reactivestreams" % V.zioInteropRS       % Test,
    "co.fs2"  %% "fs2-reactive-streams"        % V.fs2ReactiveStreams % Test
  )

  val common: Seq[ModuleID] = Seq(catsCore) ++ circe

  val telegramiumCore: Seq[ModuleID] = common ++ Seq(scalatest, scalatestplus)

  val telegramiumHigh: Seq[ModuleID] = common ++ Seq(catsEffect, scalatest) ++ http4sServer ++
    http4sClient ++ testcontainers ++ zioStreamsInterop

  val telegramiumExam: Seq[ModuleID] = common ++ Seq(catsEffect) ++ http4sClient
}
