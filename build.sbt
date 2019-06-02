ThisBuild / version      := "0.0.1"
ThisBuild / organization := "io.github.fperiodic"

val settings = Compiler.settings ++ Seq()

lazy val `telegramium-core` = project
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.telegramiumCore)

lazy val `telegramium-examples` = project
  .dependsOn(`telegramium-core`)
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.telegramiumExam)

lazy val telegramium = (project in file("."))
  .settings(
    name := "[ Tg ]"
  )
  .aggregate(
    `telegramium-core`,
    `telegramium-examples`,
  )
