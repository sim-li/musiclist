package co.lischka.musiclist.restapi.search

import akka.actor.Actor.Receive
import akka.actor.{Props, Actor}
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.search.SearchMessages.{Result, Search}


object SoundCloudSearch {
  def props() = Props()
}

class SoundCloudSearch extends Actor {
  override def receive: Receive = {
    case Search(query) => sender ! Result(List(TrackEntity(
      id=Some(0),
      url="http://abc",
      title="Soundcloud Res",
      description="My cool description"
    )))
  }
}
