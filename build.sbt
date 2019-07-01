name := "akka-graal-config"

inThisBuild(List(
  organization := "com.github.vmencik",
  version := "0.4.0",
  scalaVersion := "2.13.0",
  autoScalaLibrary := false,

  licenses := Seq("Apache-2.0" -> url("https://opensource.org/licenses/Apache-2.0")),
  homepage := Some(url("https://github.com/vmencik/akka-graal-config")),
  developers := List(Developer("vmencik", "Vlastimil Mencik", "v_mencik@hotmail.com", url("https://github.com/vmencik"))),
  scmInfo := Some(ScmInfo(url("https://github.com/vmencik/akka-graal-config"), "scm:git:git@github.com:vmencik/akka-graal-config.git")),

  pgpPublicRing := file("./travis/local.pubring.asc"),
  pgpSecretRing := file("./travis/local.secring.asc"),
  releaseEarlyWith := SonatypePublisher
))

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
