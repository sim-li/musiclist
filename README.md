# MusicList

Akka HTTP / Scala.js + Require Mobile App

Let's you share some music with your friends without going through
the hassle of convincing them of any other platform than MusicList (permalink is the buzzword).

## Run server; Live compile
    sbt "project musicListJVM" ~run

## Live compile react app
    sbt "project musicListJS" ~ios:dev


## Run node dev server and iPhone simulator
(Open in three different terminals)

    cd js
    npm start
    sbt "project musicListJS" ~ios:dev
    react-native run-ios
