import Dependencies.*

val scala3Version = "3.6.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "advent24",
    version := "0.1.0",
    scalaVersion := scala3Version,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions ++= Seq("-Wunused:all", "-source:future"),
    libraryDependencies ++= catsEffect ++ decline,
    Compile / run / fork := true,
  )
