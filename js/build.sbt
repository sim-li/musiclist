enablePlugins(SriPlatformPlugin)

name := "musiclist"

scalaVersion := "2.11.11"

resolvers += Resolver.bintrayRepo("scalajs-react-interface", "maven")

libraryDependencies ++= Seq(
  "scalajs-react-interface" %%% "core" % "2017.4.23-beta",
  "scalajs-react-interface" %%% "mobile" % "2017.5.2-beta",
  "scalajs-react-interface" %%% "universal" % "2017.5.2-beta",
  "scalajs-react-interface" %%% "platform-config-ios" % "2017.4.23-beta" % ios,
  "scalajs-react-interface" %%% "platform-config-android" % "2017.4.23-beta" % android,
  "scalajs-react-interface" %%% "navigation" % "2017.5.2-beta"
)


scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions"
)
