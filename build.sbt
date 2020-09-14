name := "text-search"

version := "1.0"

scalaVersion := "2.13.3"

connectInput in run := true

Compile / run / mainClass := Some("textsearch.SimpleSearch")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  "org.scalatest" %% "scalatest" % "3.2.0" % "test"
)

scalacOptions ++= Seq(
  "-encoding",
  "utf-8",                         // Specify character encoding used by source files.
  "-deprecation",                  // Emit warning and location for usages of deprecated APIs.
  "-explaintypes",                 // Explain type errors in more detail.
  "-feature",                      // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials",        // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds",         // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-language:postfixOps",          // Allow post fix ops
  "-unchecked",                    // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit",                   // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfatal-warnings",              // Fail the compilation if there are any warnings.
  "-Xlint:unused",                 // Enable -Wunused:imports,privates,locals,implicits.
  "-Xlint:-serial",                // Enable all except for serial. see scalac -Xlint:help for more
  "-Ywarn-dead-code",              // Warn when dead code is identified.
  "-Ywarn-extra-implicit",         // Warn when more than one implicit parameter section is defined.
  "-Ywarn-numeric-widen",          // Warn when numerics are widened.
  "-Ywarn-value-discard",          // Warn when non-Unit expression results are unused.
)

def addCommandsAlias(name: String, values: List[String]) =
  addCommandAlias(name, values.mkString(";", ";", ""))

addCommandsAlias(
  "validate",
  List(
    "clean",
    "test",
    "scalafmtCheck"
  )
)
