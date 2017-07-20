package co.lischka.musiclist.restapi.http.routes

import scala.concurrent.{ExecutionContext, Future}
import de.heikoseeberger.akkahttpcirce.CirceSupport
import co.lischka.musiclist.restapi.http.SecurityDirectives
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import co.lischka.musiclist.restapi.http.SecurityDirectives
import co.lischka.musiclist.restapi.models.{TrackEntity, UserEntityUpdate}
import co.lischka.musiclist.restapi.services.{AuthService, SearchService, UsersService}
import io.circe.generic.auto._
import io.circe.syntax._


class SearchServiceRoute(val searchService: SearchService)(implicit executionContext: ExecutionContext) extends CirceSupport {
  val route = pathPrefix("results"){
    pathEndOrSingleSlash {
      get {
        parameters("search_query") { (searchQuery) =>
          val answer: Future[List[TrackEntity]] = searchService.search(searchQuery)
          complete(answer.map(_.asJson))
        }
      }
    }
  }
}



