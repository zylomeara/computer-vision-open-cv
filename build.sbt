name := "opencv_scala"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.opencv" % "opencv" % "4.2.0" from "file:///C:/Users/Артём/IdeaProjects/computer-vision/lib/opencv-452.jar"
libraryDependencies += "com.typesafe" % "config" % "1.4.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.4"

// Scalatest
resolvers += "Artima Maven Repository" at "https://repo.artima.com/releases"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
logBuffered in Test := false

// For Scala REPL
libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.13.1"

libraryDependencies += "log4j" % "log4j" % "1.2.17"