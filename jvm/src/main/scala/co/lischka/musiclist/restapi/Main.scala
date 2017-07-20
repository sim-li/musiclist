package co.lischka.musiclist.restapi

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.http.HttpService
import co.lischka.musiclist.restapi.services.{AuthService, SearchService, TracksService, UsersService}
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
  val httpService = new HttpService(usersService, authService, searchService, tracksService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)

  import databaseService._
  import databaseService.driver.api._

  val setup = DBIO.seq(
    // Create the tables, including primary and foreign keys
    (usersService.users.schema ++ tracksService.tracks.schema).create)
  val fin = db.run(setup)

}