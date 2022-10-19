enablePlugins(GitPlugin)

ThisBuild / version := Version.mkVersion(
  "7.62.0",
  git.gitCurrentBranch.value,
  git.gitDescribedVersion.value,
  git.gitUncommittedChanges.value
)

lazy val scala3   = "3.1.3"
lazy val scala213 = "2.13.10"
lazy val scala212 = "2.12.17"

ThisBuild / scalaVersion := scala3

val buildCommit    = settingKey[String]("Build info: commit")
val buildTimestamp = settingKey[String]("Build info: timestamp")

ThisBuild / buildCommit    := Version.commit(git.gitHeadCommit.value)
ThisBuild / buildTimestamp := Version.timestamp

ThisBuild / name         := "telegramium"
ThisBuild / organization := "io.github.apimorphism"
ThisBuild / homepage     := Some(url("https://github.com/apimorphism/telegramium"))
ThisBuild / licenses     := List(("MIT", url("https://opensource.org/licenses/MIT")))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/apimorphism/telegramium"),
    "git@github.com:apimorphism/telegramium.git"
  )
)

ThisBuild / developers := List(
  Developer(
    "sherzod",
    "Sherzod Gapirov",
    "sh.gapirov@gmail.com",
    url("https://github.com/sherzodv")
  )
)

ThisBuild / publishMavenStyle := true
ThisBuild / publishTo         := sonatypePublishToBundle.value

ThisBuild / githubWorkflowJavaVersions :=
  Seq(JavaSpec.temurin("17"), JavaSpec.temurin("11"), JavaSpec.temurin("8"))

ThisBuild / githubWorkflowPublishTargetBranches := Seq.empty

ThisBuild / githubWorkflowBuildPreamble ++=
  Seq(
    WorkflowStep.Sbt(List("scalafmtCheckAll")),
    WorkflowStep.Sbt(List("scalafmtSbtCheck"))
  )

val settings = Compiler.settings ++ Seq()

lazy val `telegramium-core` = (projectMatrix in file("telegramium-core"))
  .jvmPlatform(scalaVersions = Seq(scala3, scala213, scala212))
  .enablePlugins(BuildInfoPlugin)
  .settings(settings: _*)
  .settings(libraryDependencies ++= Dependencies.telegramiumCore)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](
      buildCommit,
      buildTimestamp
    ),
    buildInfoPackage := "telegramium.bots.buildinfo"
  )

lazy val `telegramium-high` = (projectMatrix in file("telegramium-high"))
  .jvmPlatform(scalaVersions = Seq(scala3, scala213, scala212))
  .dependsOn(`telegramium-core`)
  .settings(settings: _*)
  .settings(libraryDependencies ++= Dependencies.telegramiumHigh)

lazy val `telegramium-examples` = (projectMatrix in file("telegramium-examples"))
  .jvmPlatform(scalaVersions = Seq(scala3))
  .dependsOn(
    `telegramium-core`,
    `telegramium-high`
  )
  .settings(settings: _*)
  .settings(
    publish / skip := true
  )
  .settings(libraryDependencies ++= Dependencies.telegramiumExam)

lazy val telegramium = (project in file("."))
  .settings(
    name           := "F[Tg]",
    publish / skip := true
  )
  .aggregate(
    `telegramium-core`.projectRefs ++
      `telegramium-high`.projectRefs ++
      `telegramium-examples`.projectRefs: _*
  )
