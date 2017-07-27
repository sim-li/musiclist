package co.lischka.musiclist.restapi.search

import akka.actor.Actor.Receive
import akka.actor.{Props, Actor}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.util.ByteString
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.search.SearchMessages.{Result, Search}

import scala.concurrent.{Future, ExecutionContext}


object SoundCloudSearch {
  def props()(implicit timeout: akka.util.Timeout, materializer: ActorMaterializer) = Props(new SoundCloudSearch())
}

class SoundCloudSearch(implicit timeout: akka.util.Timeout, materializer: ActorMaterializer) extends Actor {
  import context._

  val log = Logging(context.system, this)

  def httpRequest(requestUri: String): Future[String] = {
    for {
      response <- Http().singleRequest(HttpRequest(uri = requestUri))
      bytes <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
    } yield bytes.utf8String
  }

  def parseListOfTrackEntity(json: String): List[TrackEntity] = {
    val temp = json.replaceAll("\n", "").split(",")
    val titles = temp.filter(_.matches(".*title.*")).map(_.split(":")(1).split("\"")(1))
    val uri = temp.filter(_.matches(".*\"uri.*")).map(_.split("\":")(1).split("\"")(1))
    val titlesAndUri = titles.zip(uri)
    titlesAndUri.map(t => TrackEntity(title = t._1, url=t._2 , artist="", description="")).toList
  }

  override def receive: Receive = {
    case Search(query) => {

      sender ! Result(List(TrackEntity(
        id = Some(0),
        url = "http://abc",
        title = "SC Res",
        artist="fdsa",
        description = "My cool description"
      )))

//      val s = sender()
//      log.debug(s"Got Search request at sc, query ${query}")
//      val soundCloudUri = s"https://api.soundcloud.com/tracks?q=${query}&client_id=JlZIsxg2hY5WnBgtn3jfS0UYCl0K8DOg"
//
//      val answer = httpRequest(soundCloudUri).map(parseListOfTrackEntity(_))
//
//      answer.foreach({
//        log.debug("Got an answer")
//        s ! Result(_)
//      })
    }
  }
}
