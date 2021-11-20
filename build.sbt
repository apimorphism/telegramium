enablePlugins(GitPlugin)

ThisBuild / version := Version.mkVersion(
  "7.54.0",
  git.gitCurrentBranch.value,
  git.gitDescribedVersion.value,
  git.gitUncommittedChanges.value
)

lazy val scala3   = "3.0.2"
lazy val scala213 = "2.13.7"
lazy val scala212 = "2.12.15"

ThisBuild / scalaVersion       := scala3
ThisBuild / crossScalaVersions := List(scala3, scala213, scala212)

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

ThisBuild / githubWorkflowJavaVersions          := Seq("adopt@1.11", "adopt@1.8")
ThisBuild / githubWorkflowPublishTargetBranches := Seq.empty

ThisBuild / githubWorkflowBuildPreamble ++=
  Seq(
    WorkflowStep.Sbt(List("scalafmtCheckAll")),
    WorkflowStep.Sbt(List("scalafmtSbtCheck"))
  )

val settings = Compiler.settings ++ Seq()

lazy val `telegramium-core` = project
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

lazy val `telegramium-high` = project
  .dependsOn(`telegramium-core`)
  .settings(settings: _*)
  .settings(libraryDependencies ++= Dependencies.telegramiumHigh)

lazy val `telegramium-examples` = project
  .dependsOn(
    `telegramium-core`,
    `telegramium-high`
  )
  .settings(settings: _*)
  .settings(
    crossScalaVersions := Nil,
    publish / skip     := true
  )
  .settings(libraryDependencies ++= Dependencies.telegramiumExam)

lazy val telegramium = (project in file("."))
  .settings(
    name               := "F[Tg]",
    publish / skip     := true,
    crossScalaVersions := Nil
  )
  .aggregate(
    `telegramium-core`,
    `telegramium-high`,
    `telegramium-examples`
  )
