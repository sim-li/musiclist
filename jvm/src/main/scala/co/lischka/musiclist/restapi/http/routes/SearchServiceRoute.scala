package co.lischka.musiclist.restapi.http.routes

import akka.actor.Props
import akka.stream.ActorMaterializer
import akka.util.Timeout
import co.lischka.musiclist.restapi.search.{SearchMessages, SearchExecutor}
import scala.concurrent.{ExecutionContext, Future}
import de.heikoseeberger.akkahttpcirce.CirceSupport
import co.lischka.musiclist.restapi.http.SecurityDirectives
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import co.lischka.musiclist.restapi.http.SecurityDirectives
import co.lischka.musiclist.restapi.models.{TrackEntity, UserEntityUpdate}
import co.lischka.musiclist.restapi.services.{AuthService, YoutubeSearchService, UsersService}
import io.circe.generic.auto._
import io.circe.syntax._
import scala.concurrent.duration._
import akka.pattern.ask
import SearchMessages._


class SearchServiceRoute(val searchService: YoutubeSearchService)(implicit executionContext: ExecutionContext,
                                                                  system: akka.actor.ActorSystem,
                                                                  materializer: ActorMaterializer
) extends CirceSupport {
  val route = pathPrefix("results") {
    pathEndOrSingleSlash {
      get {
        parameters("search_query") { (query: String) =>
          implicit val askTimeout: Timeout = 3.seconds
          val actor = system.actorOf(Props[SearchExecutor])
          onSuccess((actor ? Search(query)).mapTo[Result]) { result =>
            complete(result.data.map(_.asJson))
          }
        }
      }
    }
  }
}
