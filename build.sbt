ThisBuild / version      := "0.0.1"
ThisBuild / organization := "io.github.fperiodic"

val settings = Compiler.settings ++ Seq()

lazy val `telegramium-core` = project
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.telegramiumCore)

lazy val `telegramium-high` = project
  .dependsOn(`telegramium-core`)
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.telegramiumCore)

lazy val `telegramium-examples` = project
  .dependsOn(
    `telegramium-core`,
    `telegramium-high`,
  )
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.telegramiumExam)

lazy val telegramium = (project in file("."))
  .settings(
    name := "F[Tg]"
  )
  .aggregate(
    `telegramium-core`,
    `telegramium-high`,
    `telegramium-examples`,
  )
