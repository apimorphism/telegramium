ThisBuild / version      := "0.0.1-SNAPSHOT"
ThisBuild / name         := "telegramium"
ThisBuild / organization := "io.github.fperiodic"

ThisBuild / homepage   := Some(url("https://github.com/apimorphism/telegramium"))
ThisBuild / licenses   := List(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))
ThisBuild / scmInfo    := Some(ScmInfo(
                            url("https://github.com/apimorphism/telegramium"),
                            "git@github.com:apimorphism/telegramium.git"
                          ))
ThisBuild / developers := List(Developer(
                            "sherzod", "Sherzod Gapirov",
                            "sh.gapirov@gmail.com",
                            url("https://github.com/sherzodv")
                          ))

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
