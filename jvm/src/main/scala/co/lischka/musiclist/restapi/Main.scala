package co.lischka.musiclist.restapi

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.http.HttpService
import co.lischka.musiclist.restapi.models.UserEntity
import co.lischka.musiclist.restapi.services.{SearchService, AuthService, UsersService}
import co.lischka.musiclist.restapi.utils.{Config, DatabaseService, FlywayService}
import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Failure, Success}



object Main extends App with Config {
  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val flywayService = new FlywayService(jdbcUrl, dbUser, dbPassword)
  flywayService.migrateDatabaseSchema

  val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)

  val usersService = new UsersService(databaseService)
  val authService = new AuthService(databaseService)(usersService)

  val searchService = new SearchService()
  val httpService = new HttpService(usersService, authService, searchService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)

  import databaseService._
  import databaseService.driver.api._

  val schema = searchService.schema
  db.run(DBIO.seq(
    schema.create))
}
