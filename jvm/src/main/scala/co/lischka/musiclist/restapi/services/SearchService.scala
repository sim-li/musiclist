package co.lischka.musiclist.restapi.services

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.stream.ActorMaterializer
import akka.util.{ByteString}
import scala.concurrent.{ExecutionContext, Future}
import io.circe.generic.auto._
import io.circe.syntax._

class SearchService() (
  implicit executionContext: ExecutionContext,
  system: akka.actor.ActorSystem,
  materializer: ActorMaterializer
){

  def search(title: String): Future[String] = {
    val youtubeUri = s"https://www.googleapis.com/youtube/v3/search/?q=${title}&maxResults=25&part=snippet&key=" +
    "AIzaSyBtUanlaGBqScDggFrqUkMVa63sYORidZg"

    val f: Future[String] = for {
      response <- Http().singleRequest(HttpRequest(uri = youtubeUri))
      bytes <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
    } yield bytes.utf8String
    f

  }
}
