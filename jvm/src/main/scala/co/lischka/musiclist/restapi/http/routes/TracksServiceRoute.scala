package co.lischka.musiclist.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.services.TracksService
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class TracksServiceRoute(val tracksService: TracksService)(implicit executionContext: ExecutionContext) extends CirceSupport {

  import StatusCodes._
  import tracksService._

  val route = pathPrefix("musicList") {
    (path("track") & get) {
      complete(getTracks().map(_.asJson))
    } ~
      (path("track" / LongNumber) & get) { id =>
        complete(getTrackById(id).map(_.asJson))
      } ~
      (path("track") & post) {
        entity(as[TrackEntity]) { track =>
          complete(createTrack(track).map(_.asJson))
        }
      } ~
      (path("track") & put) {
        entity(as[TrackEntity]) { track =>
          complete(updateTrack(track).map(_.asJson))
        }
      } ~
      (path("track" / LongNumber) & delete) { trackId =>
        complete(deleteTrack(trackId).map(_.asJson))
      }
  }
}