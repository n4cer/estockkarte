name := """eStockkarte"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc, javaJdbc, "org.postgresql" % "postgresql" % "9.4.1208",
  cache,
  javaWs,
  evolutions,
  "org.mindrot" % "jbcrypt" % "0.3m"
)