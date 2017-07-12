package co.lischka.musiclist.restapi.http.routes

import scala.concurrent.ExecutionContext
import de.heikoseeberger.akkahttpcirce.CirceSupport

import co.lischka.musiclist.restapi.http.SecurityDirectives
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber

import co.lischka.musiclist.restapi.http.SecurityDirectives
import co.lischka.musiclist.restapi.models.UserEntityUpdate
import co.lischka.musiclist.restapi.services.{AuthService, UsersService}
import io.circe.generic.auto._
import io.circe.syntax._

case class SearchResult(result: String)

class SearchServiceRoute()(implicit executionContext: ExecutionContext) extends CirceSupport {

  val route = path("search"){
    pathEndOrSingleSlash {
      post {
        complete(SearchResult("Test").asJson)
      }
    }
  }
}

