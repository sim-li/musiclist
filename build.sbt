name := "musiclist"

version := "1.0"

scalaVersion := "2.12.1"

enablePlugins(ScalaJSPlugin)
libraryDependencies ++= Seq(
  "com.lihaoyi" %%% "scalatags" % "0.6.3",
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.1"

)