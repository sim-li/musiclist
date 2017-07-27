package co.lischka.musiclist.restapi

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.http.HttpService
import co.lischka.musiclist.restapi.models.{MusicListEntity, TrackEntity, TrackAtListEntity, UserEntity}
import co.lischka.musiclist.restapi.services.{AuthService, MusicListService, YoutubeSearchService, TrackAtListService, TracksService, UsersService}
import co.lischka.musiclist.restapi.utils.{Config, DatabaseService, FlywayService}
import co.lischka.musiclist.restapi.parsers.SoundcloudParser

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import cats.syntax.either._
import io.circe._, io.circe.parser._
import io.circe.parser.decode
import io.circe.syntax._

object Main extends App with Config {
  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher

  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val flywayService = new FlywayService(jdbcUrl, dbUser, dbPassword)

  val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)

  val usersService = new UsersService(databaseService)

  val authService = new AuthService(databaseService)(usersService)

  val searchService = new YoutubeSearchService()

  val tracksService = new TracksService(databaseService)
  val musicListService = new MusicListService(databaseService, tracksService)
  val trackAtListService = new TrackAtListService(databaseService)
  val httpService = new HttpService(usersService, authService, searchService, tracksService, musicListService, trackAtListService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)

  import databaseService._
  import databaseService.driver.api._

  val setup = DBIO.seq(
    (tracksService.tracks.schema ++ musicListService.musicList.schema ++ trackAtListService.trackAtList.schema).create)
  val fin = db.run(setup)


  //Creates two musiclists, the first one has two tracks.
  /*
  val muli1 = new MusicListEntity(title="t1", description="d1", permalink = "p1")
  val muliPers1 = musicListService.createList(muli1)

  val track1 = new TrackEntity(url = "u1", title = "t1", description = "d1")
  val trackPers1 = tracksService.createTrack(track1)


  val muli2 = new MusicListEntity(title="t2", description="d2", permalink = "p2")
  val muliPers2 = musicListService.createList(muli2)

  val track2 = new TrackEntity(url = "u2", title = "t2", description = "d2")
  val trackPers2 = tracksService.createTrack(track2)

  val trackx = new TrackEntity(url = "u1t2", title = "t1t2", description = "d1t2  ")
  val trackPersx = tracksService.createTrack(trackx)

  // Waits for track and musiclist to be persisted, then creates TrackAtList
  for {
    t <- trackPers1
    m <- muliPers1
  } yield tracksService.createTrackAtList(TrackAtListEntity(t.id, m.id))

  for {
    t <- trackPers2
    m <- muliPers2
  } yield tracksService.createTrackAtList(TrackAtListEntity(t.id, m.id))

  for {
    t <- trackPersx
    m <- muliPers1
  } yield tracksService.createTrackAtList(TrackAtListEntity(t.id, m.id))
  */

  musicListService.getTracksAtList("p1").foreach(print(_))



}
