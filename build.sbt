name := "turtleDSL_scala"

version := "1.0"

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.11.0-M7"

autoCompilerPlugins := true
 
addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.11.0-M7")
 
scalacOptions += "-P:continuations:enable"

scalacOptions += "-feature"

scalacOptions += "-deprecation"

libraryDependencies += "net.liftweb"%"lift-json_2.10"%"2.6-M2"

libraryDependencies += "org.scalatest" % "scalatest_2.11.0-M7" % "2.0.1-SNAP4" % "test"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.0-M7"

//libraryDependencies += "org.testng"%"testng"%"6.8.5"%"test"
