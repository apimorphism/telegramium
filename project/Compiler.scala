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
    scalacOptions ++= options ++
      (if (scalaBinaryVersion.value.startsWith("2.12")) Seq("-Ypartial-unification") else Seq.empty) ++
      (if (scalaBinaryVersion.value.startsWith("2")) Seq("-Xsource:3") else Seq.empty) ++
      // Compile against Java 8's API even when building on a newer JDK, which can otherwise emit calls that fail on 8
      // (e.g. ByteBuffer.flip() returning ByteBuffer). JDK 8 itself has no data for -release, and needs none.
      (if (sys.props("java.specification.version") != "1.8") Seq("-release", "8") else Seq.empty)
  )

}
