package co.lischka.musiclist.restapi.search

import akka.actor.{Actor, Props}
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.search.SearchMessages.{Result, Search}


object YoutubeSearch {
  def props() = Props()
}

class YoutubeSearch extends Actor {
  override def receive: Receive = {
    case Search(query) => sender ! Result(List(TrackEntity(
      id=Some(0),
      url="http://abc",
      title="Youtube Res",
      description="My cool description"
    )))
  }
}
