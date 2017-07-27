package co.lischka.musiclist.restapi.search

import akka.actor.Actor.Receive
import akka.actor.{Props, Actor}
import akka.event.Logging
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.search.SearchMessages.{Result, Search}

import scala.concurrent.ExecutionContext


object SoundCloudSearch {
  def props()(implicit timeout: akka.util.Timeout) = Props(new SoundCloudSearch())
}

class SoundCloudSearch(implicit timeout: akka.util.Timeout) extends Actor {
  import context._

  val log = Logging(context.system, this)

  override def receive: Receive = {
    case Search(query) => {
      log.debug("Got Search request at sc")
      sender ! Result(List(TrackEntity(
        id=Some(0),
        url="http://abc",
        title="Soundcloud Res",
        description="My cool description"
      )))
    }
  }
}
