name := "turtleDSL_scala"

version := "1.0"

scalaVersion := "2.11.0-M4"

autoCompilerPlugins := true
 
addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.11.0-M4")
 
scalacOptions += "-P:continuations:enable"

libraryDependencies += "net.liftweb"%"lift-json_2.10"%"2.5.1"

libraryDependencies += "org.scalatest" % "scalatest_2.11.0-M3" % "1.9.1b" % "test"

//libraryDependencies += "org.testng"%"testng"%"6.8.5"%"test"
