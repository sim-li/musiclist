package co.lischka.musiclist.restapi.services

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.util.ByteString
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.parsers.YoutubeParser

import scala.concurrent.{ExecutionContext, Future}

abstract class SearchService()  (
implicit executionContext: ExecutionContext, system: akka.actor.ActorSystem, materializer: ActorMaterializer
) {

  def search(title: String): Future[List[TrackEntity]]

  def httpRequest(requestUri: String): Future[String] = {
    for {
      response <- Http().singleRequest(HttpRequest(uri = requestUri))
      bytes <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
    } yield bytes.utf8String
  }
}
