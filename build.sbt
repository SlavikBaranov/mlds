organization in ThisBuild := "football"

version in ThisBuild := "0.1.0-SNAPSHOT"

//scalaVersion in ThisBuild := "2.11.7"
scalaVersion in ThisBuild := "2.10.5"

resolvers in ThisBuild ++= Seq(
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
)

net.virtualvoid.sbt.graph.Plugin.graphSettings
