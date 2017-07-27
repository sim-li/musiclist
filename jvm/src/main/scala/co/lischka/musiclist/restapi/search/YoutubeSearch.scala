package co.lischka.musiclist.restapi.search

import akka.actor.{Actor, Props}
import akka.event.Logging
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.search.SearchMessages.{Result, Search}
import scala.concurrent.ExecutionContext


object YoutubeSearch {
  def props()(implicit timeout: akka.util.Timeout) = Props(new YoutubeSearch())
}

class YoutubeSearch()(implicit timeout: akka.util.Timeout) extends Actor {
  import context._

  val log = Logging(context.system, this)

  override def receive: Receive = {
    case Search(query) => {
      log.debug("Got Search request at youtube")
      sender ! Result(List(TrackEntity(
        id = Some(0),
        url = "http://abc",
        title = "Youtube Res",
        artist="fdsa",
        description = "My cool description"
      )))
    }
  }
}
