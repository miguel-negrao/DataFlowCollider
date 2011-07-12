import sbt._

class MyProject( info: ProjectInfo ) extends DefaultProject( info ) with AutoCompilerPlugins with AkkaProject {
  val akkaSTM    = akkaModule("stm")
  val akkaRemote = akkaModule("remote")
  
  val jsonlift = "net.liftweb" %% "lift-json" % "2.4-M1" withSources()
  val scalazCore = "org.scalaz" %% "scalaz-core" % "6.0.1" withSources()
  val dep3 = "org.scala-lang" % "scala-swing" % "2.9.0-1" withSources()

  val sc = "de.sciss" %% "scalacollider" % "0.24" withSources()
  //val scSources = "de.sciss" %% "scalacollider" % "0.24" % "sources" classifier "sources"
  //val dep2 = "de.sciss" % "swingosc" % "0.65" from "http://sourceforge.net/projects/swingosc/files/swingosc/0.65/SwingOSC-plain.jar"

  val ktl = "org.friendlyvirus.mn" %% "scalamidiktl" % "0.1"

  val cont = compilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.0-1")
  override def compileOptions = super.compileOptions ++ compileOptions("-P:continuations:enable")
  override def consoleOptions = compileOptions
}