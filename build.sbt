name := "Exercises"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += Resolver.sonatypeRepo("releases")

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  //"-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
  "-Xfuture", // Turn on future language features.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match", // Pattern match may not be typesafe.
  "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  "-Ypartial-unification", // Enable partial unification in type constructor inference
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  //"-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)

//scalacOptions ++= Seq(
//  "-Yno-predef",   // no automatic import of Predef (removes irritating implicits)
//  "-Yno-imports"  // no automatic imports at all; all symbols must be imported explicitly
//)

//scalacOptions in (Compile, console) --= Seq("-Ywarn-unused:imports", "-Xfatal-warnings")

/* Macro-related stuff */

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.full)

scalacOptions += "-Xplugin-require:macroparadise"
scalacOptions in(Compile, console) ~= (_ filterNot (_ contains "paradise")) // macroparadise plugin doesn't work in repl yet.

/* Other Plugins */

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")

enablePlugins(TutPlugin)

// enablePlugins(MicrositesPlugin)

/* Dependencies */

val catsVersion = "1.1.0"
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-macros", // Macros used by Cats syntax (required).
  "org.typelevel" %% "cats-kernel", // Small set of basic type classes (required).
  "org.typelevel" %% "cats-core", // Most core type classes and functionality (required).
  "org.typelevel" %% "cats-laws", // Laws for testing type class instances.
  "org.typelevel" %% "cats-macros", // Macros used by Cats syntax (required).
  "org.typelevel" %% "cats-kernel", // Small set of basic type classes (required).
  "org.typelevel" %% "cats-core", // Most core type classes and functionality (required).
  "org.typelevel" %% "cats-laws", // Laws for testing type class instances.
  "org.typelevel" %% "cats-free" // Free structures such as the free monad, and supporting type classes.
).map(_ % catsVersion withJavadoc())

// general? dependencies
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "1.0.0-RC",
  "com.github.mpilquist" %% "simulacrum" % "0.12.0",
  "org.atnos" %% "eff" % "5.1.0",
  "io.frees" %% "iota-core" % "0.3.6",
  "com.roundeights" %% "hasher" % "1.2.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.1.0",
  "com.typesafe.akka" %% "akka-http" % "10.1.1"
).map(_ withJavadoc())

val circeVersion = "0.9.3"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion withJavadoc())

// Akka
val akkaVersion = "2.5.12"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor",
  "com.typesafe.akka" %% "akka-stream",
  "com.typesafe.akka" %% "akka-persistence"
).map(_ % akkaVersion withJavadoc())

val alpakkaVersion = "0.18"
libraryDependencies ++= Seq(
  // See
  // - https://developer.lightbend.com/docs/alpakka/current/examples/csv-samples.html
  // - https://developer.lightbend.com/docs/alpakka/current/data-transformations/csv.html
  "com.lightbend.akka" %% "akka-stream-alpakka-csv",

  // See https://developer.lightbend.com/docs/alpakka/current/slick.html
  "com.lightbend.akka" %% "akka-stream-alpakka-slick"
).map(_ % alpakkaVersion withJavadoc())

// scalaz
val scalazVersion = "7.2.22"
libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core",
  "org.scalaz" %% "scalaz-effect",
  "org.scalaz" %% "scalaz-concurrent"
).map(_ % scalazVersion withJavadoc())

// fs2
val fs2Version = "0.10.4"
libraryDependencies ++= Seq(
  "co.fs2" %% "fs2-core",
  "co.fs2" %% "fs2-io"
).map(_ % fs2Version withJavadoc())

// monix
val monixVersion = "3.0.0-RC1"
libraryDependencies ++= Seq(
  "io.monix" %% "monix"
).map(_ % monixVersion withJavadoc())

// Testing Dependencies
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-testkit" % catsVersion, // lib for writing tests for type class instances using laws
  "org.scalatest" %% "scalatest" % "3.0.5",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.12"
).map(_ % Test withJavadoc())