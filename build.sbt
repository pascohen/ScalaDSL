name := "turtleDSL_scala"

version := "1.0"

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.11.0-SNAPSHOT"

autoCompilerPlugins := true
 
addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.11.0-SNAPSHOT")
 
scalacOptions += "-P:continuations:enable"

scalacOptions += "-feature"

scalacOptions += "-deprecation"

libraryDependencies += "net.liftweb"%"lift-json_2.10"%"2.5.1"

libraryDependencies += "org.scalatest" % "scalatest_2.11.0-M5" % "2.0.M7" % "test"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.0-SNAPSHOT"

//libraryDependencies += "org.testng"%"testng"%"6.8.5"%"test"
