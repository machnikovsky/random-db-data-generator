ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "random-db-data-generator",
    idePackagePrefix := Some("pl.machnikovsky.generator")
  )

libraryDependencies ++= Seq(
  "io.github.etspaceman" %% "scalacheck-faker" % "7.0.0",
  "co.fs2"               %% "fs2-core"         % "3.3.0",
  "co.fs2"               %% "fs2-io"           % "3.3.0",
  "com.beachape"         %% "enumeratum"       % "1.7.0",
  "org.json4s"           %% "json4s-jackson"   % "4.0.6"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full)
