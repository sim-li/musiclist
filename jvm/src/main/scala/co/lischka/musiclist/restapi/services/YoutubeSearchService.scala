package co.lischka.musiclist.restapi.services

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.util.ByteString
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.models.db.TrackEntityTable
import co.lischka.musiclist.restapi.parsers.YoutubeParser

import scala.concurrent.{ExecutionContext, Future}

class YoutubeSearchService() (
  implicit executionContext: ExecutionContext, system: akka.actor.ActorSystem, materializer: ActorMaterializer
) extends SearchService {

  def search(title: String): Future[List[TrackEntity]] = {
    val youtubeUri = s"https://www.googleapis.com/youtube/v3/search/?q=${title}&maxResults=25&part=snippet&key=" +
    "AIzaSyBtUanlaGBqScDggFrqUkMVa63sYORidZg"
    httpRequest(youtubeUri).map(YoutubeParser.parseTracks(_))
  }
}
