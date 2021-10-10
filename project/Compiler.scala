import sbt._
import Keys._

object Compiler {

  val options = Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf-8",                  // Specify character encoding used by source files.
    "-feature",               // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds",         // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-unchecked"                     // Enable additional warnings where generated code depends on assumptions.
  )

  val settings = Seq(
    scalacOptions ++=
      (if (scalaBinaryVersion.value.startsWith("2.12"))
         options :+ "-Ypartial-unification"
       else options)
  )

}
