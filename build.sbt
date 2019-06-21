name := "akka-graal-config"

ThisBuild / organization := "com.github.vmencik"

ThisBuild / version := "0.3.2"

ThisBuild / scalaVersion := "2.13.0"

ThisBuild / autoScalaLibrary := false

lazy val scala212 = "2.12.8"
lazy val scala213 = "2.13.0"
lazy val supportedScalaVersions = List(scala213, scala212)

lazy val actor = (project in file("akka-actor"))
  .settings(
    name := "graal-akka-actor",
    crossScalaVersions := supportedScalaVersions,
    unmanagedResourceDirectories in Compile += sourceDirectory.value / "main" / s"resources-${scalaBinaryVersion.value}",
    libraryDependencies ++= Seq(
      // required for substitutions
      // make sure the version matches GraalVM version used to run native-image
      "com.oracle.substratevm" % "svm" % "19.0.2" % Provided
    )
  )

lazy val slf4j = (project in file("akka-slf4j"))
  .dependsOn(actor)
  .settings(
    name := "graal-akka-slf4j"
  )

lazy val stream = (project in file("akka-stream"))
  .dependsOn(actor)
  .settings(
    name := "graal-akka-stream"
  )

lazy val http = (project in file("akka-http"))
  .dependsOn(stream)
  .settings(
    name := "graal-akka-http",
    crossScalaVersions := supportedScalaVersions,
    unmanagedResourceDirectories in Compile += sourceDirectory.value / "main" / s"resources-${scalaBinaryVersion.value}"
  )

// do not publish root project
publish / skip := true

crossScalaVersions := Nil
