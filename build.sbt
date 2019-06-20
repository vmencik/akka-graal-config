name := "akka-graal-config"

ThisBuild / organization := "com.github.vmencik"

ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.12.8"

ThisBuild / autoScalaLibrary := false

lazy val actor = (project in file("akka-actor"))
  .settings(
    name := "graal-akka-actor",
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
    name := "graal-akka-http"
  )

// do not publish root project
publish / skip := true
