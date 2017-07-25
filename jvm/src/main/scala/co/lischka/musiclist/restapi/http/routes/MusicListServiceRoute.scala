package co.lischka.musiclist.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import co.lischka.musiclist.restapi.models.{MusicListEntity, TrackEntity}
import co.lischka.musiclist.restapi.services.{MusicListService, TracksService}
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class MusicListServiceRoute(val musicListService: MusicListService, tracksService: TracksService)(implicit executionContext: ExecutionContext) extends CirceSupport {

  import StatusCodes._
  import musicListService._
  import tracksService._

  val route =
    (path("musicList") & get) {
      complete(getLists().map(_.asJson))
    } ~
      (path("musicList" / LongNumber) & get) { id =>
        complete(getListById(id).map(_.asJson))
      } ~
      (path("musicList" / Segment) & get) { str =>
        complete(getTracksAtList(str).map(_.asJson))
      } ~
      (path("musicList") & post) {
        entity(as[MusicListEntity]) { list =>
          complete(createList(list).map(_.asJson))
        }
      } ~
      /*pathPrefix("musicList") {
        (path("track" / LongNumber) & get) { id =>
          complete(getTrackById(id).map(_.asJson))
        }
      } ~*/
      (path("musicList") & put) {
        entity(as[MusicListEntity]) { list =>
          complete(updateList(list).map(_.asJson))
        }
      } ~
      (path("musicList" / LongNumber) & delete) { listId =>
        complete(deleteList(listId).map(_.asJson))
      } ~
      (path("musicList" / LongNumber) & post) {
        id => {
          entity(as[TrackEntity]) { track =>
            complete(insertTrackAtList(track, Seq(id)).map(_.asJson))
          }
        }
      }
}

/*pathPrefix("musicList") {
    pathEndOrSingleSlash {
      get {
        complete(getLists().map(_.asJson))
      }
    } ~
      post {
        entity(as[MusicListEntity]) { list =>
          complete(createList(list).map(_.asJson))
        }
      } ~
      put {
        entity(as[MusicListEntity]) { list =>
          complete(updateList(list).map(_.asJson))
        }
      } ~
      pathPrefix(LongNumber) { id =>
        pathEndOrSingleSlash {
          get {
            complete(getListById(id).map(_.asJson))
          }
        } ~
          delete {
            complete(deleteList(id).map(_.asJson))
          } ~
          post {
            entity(as[TrackEntity]) { track =>
              complete(insertTrackAtList(track, Seq(id)).map(_.asJson))
            }
          }
      } ~
      pathPrefix("tracks") {
        pathEndOrSingleSlash {
          get {
            complete(getTracks().map(_.asJson))
          }
        } ~
        pathPrefix(LongNumber) { id =>
          pathEndOrSingleSlash {
            get {
              complete(getTrackById(id).map(_.asJson))
            }
          }
        }
      }
  }
}*/



