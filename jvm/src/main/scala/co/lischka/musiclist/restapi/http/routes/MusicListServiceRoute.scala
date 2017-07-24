package co.lischka.musiclist.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import de.heikoseeberger.akkahttpcirce.CirceSupport
import co.lischka.musiclist.restapi.models.MusicListEntity
import co.lischka.musiclist.restapi.services.MusicListService
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class MusicListServiceRoute(val musicListService: MusicListService)(implicit executionContext: ExecutionContext) extends CirceSupport {

  import StatusCodes._
  import musicListService._

  val route =
    (path("lists") & get) {
      complete(getLists().map(_.asJson))
    } ~
      (path("list" / IntNumber) & get) { id =>
        complete(getListById(id).map(_.asJson))
      } ~
      (path("list") & post) {
        entity(as[MusicListEntity]) { list =>
          complete(createList(list).map(_.asJson))
        }
      } ~
      (path("list") & put) {
        entity(as[MusicListEntity]) { list =>
          complete(updateList(list).map(_.asJson))
        }
      } ~
      (path("list" / IntNumber) & delete) { listId =>
        complete(deleteList(listId).map(_.asJson))
      }
}
