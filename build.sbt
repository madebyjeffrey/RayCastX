organization := "org.iaefai"

name := "RayCastX"

version := "1.0"

scalaVersion := "2.10.3"

mainClass := Some("Main")

libraryDependencies ++= Seq(
  "org.scalanlp" % "breeze_2.10" % "0.5.2",
  "org.scalanlp" % "breeze-viz_2.10" % "0.5.2"
)

// this must be locally installed

libraryDependencies += "default" % "pureimage_2.10" % "0.1-SNAPSHOT"

