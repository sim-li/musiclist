package co.lischka.musiclist.restapi

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.http.HttpService
import co.lischka.musiclist.restapi.models.{MusicListEntity, TrackEntity, TrackAtListEntity, UserEntity}
import co.lischka.musiclist.restapi.services.{AuthService, MusicListService, YoutubeSearchService, TrackAtListService, TracksService, UsersService}
import co.lischka.musiclist.restapi.utils.{Config, DatabaseService, FlywayService}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


object Main extends App with Config {
  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher

  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val flywayService = new FlywayService(jdbcUrl, dbUser, dbPassword)
  //flywayService.migrateDatabaseSchema

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
    // Create the tables
    (usersService.users.schema ++ tracksService.tracks.schema ++ musicListService.musicList.schema ++ trackAtListService.trackAtList.schema).create)
  val fin = db.run(setup)

  /*val u = new UserEntity(username = "test", password = "test")
  authService.signUp(u)
  val t = new TrackEntity(url = "www", title = "title", description = "pop")
  tracksService.createTrack(t)


  // Save list from database
  //val l = new MusicListEntity(permalink = "www.xyz.se")
  //musicListService.createList(l)

  val someEntry = musicListService.getListById(3L) map {
    case Some(ml) => musicListService.updateList(ml.copy(permalink = "1new_www.xyz.se"))
    case None => print("error")
  }

  val muli = new MusicListEntity(permalink = "www.abc.se")
  val cremuli1 = new TrackEntity(url = "www", title = "title", description = "rock")
  val me = new TrackAtListEntity(Some(5L), Some(3L))
  tracksService.createTrackAtList(me)
  tracksService.deleteTrack(1L)

  val list1 = new MusicListEntity(permalink = "www.com")
  val list2 = new MusicListEntity(permalink = "www.com.de")
  val cremuli = new TrackEntity(url = "www", title = "title", description = "jazz")
  val cremul = new TrackEntity(url = "www", title = "title", description = "house")
  tracksService.insertTrackAtList(cremuli, Seq(3L))
  tracksService.insertTrackAtList(cremul, Seq(3L))
  musicListService.insertListAtTrack(list1, Seq(10L))
  musicListService.insertListAtTrack(list2, Seq(10L))*/

}
