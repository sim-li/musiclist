name := "Foo root project"

scalaVersion in ThisBuild := "2.11.8"

lazy val root = project.in(file(".")).
  aggregate(fooJS, fooJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val foo = crossProject.in(file(".")).
  settings(
    name := "foo",
    version := "0.1-SNAPSHOT"
  ).
  jvmSettings(
    libraryDependencies ++= {
      mainClass in (Compile,run) := Some("de.co.lischka.restapi.Main")
      val akkaV = "10.0.0"
      val scalaTestV = "3.0.1"
      val slickVersion = "3.2.0-M2"
      val circeV = "0.6.1"
      Seq(
        "com.typesafe.akka" %% "akka-http-core" % akkaV,
        "com.typesafe.akka" %% "akka-http" % akkaV,
        "de.heikoseeberger" %% "akka-http-circe" % "1.11.0",

        "com.typesafe.slick" %% "slick" % slickVersion,
        "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
        "org.flywaydb" % "flyway-core" % "3.2.1",

        "com.zaxxer" % "HikariCP" % "2.4.5",
        "org.slf4j" % "slf4j-nop" % "1.6.4",

        "io.circe" %% "circe-core" % circeV,
        "io.circe" %% "circe-generic" % circeV,
        "io.circe" %% "circe-parser" % circeV,

        "org.scalatest" %% "scalatest" % scalaTestV % "test",
        "com.typesafe.akka" %% "akka-http-testkit" % akkaV % "test",
        "ru.yandex.qatools.embed" % "postgresql-embedded" % "1.15" % "test"
      )
    }).jsSettings(
      // Add JS-specific settings here
    )

lazy val fooJVM = foo.jvm
lazy val fooJS = foo.js