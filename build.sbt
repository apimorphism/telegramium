enablePlugins(GitPlugin)

ThisBuild / version := Version.mkVersion(
  "2.49.0",
  git.gitCurrentBranch.value,
  git.gitHeadCommit.value,
  git.gitDescribedVersion.value,
  git.gitUncommittedChanges.value
)

lazy val scala213 = "2.13.3"
lazy val scala212 = "2.12.12"

ThisBuild / scalaVersion := scala213
ThisBuild / crossScalaVersions := List(scala213, scala212)

val buildCommit    = settingKey[String]("Build info: commit")
val buildTimestamp = settingKey[String]("Build info: timestamp")

ThisBuild / buildCommit       := Version.commit(git.gitHeadCommit.value)
ThisBuild / buildTimestamp    := Version.timestamp

ThisBuild / name              := "telegramium"
ThisBuild / organization      := "io.github.apimorphism"
ThisBuild / homepage          := Some(url("https://github.com/apimorphism/telegramium"))
ThisBuild / licenses          := List(("MIT", url("http://opensource.org/licenses/MIT")))
ThisBuild / scmInfo           := Some(ScmInfo(
                                   url("https://github.com/apimorphism/telegramium"),
                                   "git@github.com:apimorphism/telegramium.git"
                                 ))
ThisBuild / developers        := List(Developer(
                                   "sherzod", "Sherzod Gapirov",
                                   "sh.gapirov@gmail.com",
                                   url("https://github.com/sherzodv")
                                 ))

ThisBuild / publishMavenStyle := true
ThisBuild / publishTo         := Some(
                                   if (isSnapshot.value)
                                     Opts.resolver.sonatypeSnapshots
                                   else
                                     Opts.resolver.sonatypeStaging
                                 )

usePgpKeyHex("ACBE704D")

ThisBuild / githubWorkflowJavaVersions := Seq("adopt@1.11", "adopt@1.8")
ThisBuild / githubWorkflowPublishTargetBranches := Seq.empty

val settings = Compiler.settings ++ Seq()

lazy val `telegramium-core` = project
  .enablePlugins(BuildInfoPlugin)
  .settings(settings: _*)
  .settings(libraryDependencies ++= Dependencies.telegramiumCore)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](
      buildCommit,
      buildTimestamp,
    ),
    buildInfoPackage := "telegramium.bots.buildinfo",
  )

lazy val `telegramium-high` = project
  .dependsOn(`telegramium-core`)
  .settings(settings: _*)
  .settings(libraryDependencies ++= Dependencies.telegramiumHigh)

lazy val `telegramium-examples` = project
  .dependsOn(
    `telegramium-core`,
    `telegramium-high`,
  )
  .settings(settings: _*)
  .settings(
    skip in publish := true
  )
  .settings(libraryDependencies ++= Dependencies.telegramiumExam)

lazy val telegramium = (project in file("."))
  .settings(
    name := "F[Tg]",
    skip in publish := true,
    crossScalaVersions := Nil,
  )
  .aggregate(
    `telegramium-core`,
    `telegramium-high`,
    `telegramium-examples`,
  )
