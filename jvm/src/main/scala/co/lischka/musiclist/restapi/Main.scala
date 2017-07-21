package co.lischka.musiclist.restapi

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.http.HttpService
import co.lischka.musiclist.restapi.models.{MusicListEntity, TrackEntity, UserEntity}
import co.lischka.musiclist.restapi.services.{AuthService, MusicListService, SearchService, TrackAtListService, TracksService, UsersService}
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

  val searchService = new SearchService()
  val tracksService = new TracksService(databaseService)
  val musicListService = new MusicListService(databaseService)
  val trackAtListService = new TrackAtListService(databaseService)
  val httpService = new HttpService(usersService, authService, searchService, tracksService, musicListService, trackAtListService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)

  import databaseService._
  import databaseService.driver.api._

  val setup = DBIO.seq(
    // Create the tables
    (usersService.users.schema ++ tracksService.tracks.schema ++ musicListService.musicList.schema ++ trackAtListService.trackAtList.schema).create)
  val fin = db.run(setup)

  val u = new UserEntity(username = "test", password = "test")
  authService.signUp(u)
  val t = new TrackEntity(url = "www", title = "title", description = "pop")
  tracksService.createTrack(t)
  val l = new MusicListEntity(permalink = "www.xyz.se")
  musicListService.createList(l)
}