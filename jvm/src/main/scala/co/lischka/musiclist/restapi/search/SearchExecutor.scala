package co.lischka.musiclist.restapi.search

import akka.actor.{Actor, Props}
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.search.SearchMessages.{Search, Result}
import akka.pattern.ask

object SearchExecutor {
  def props() = Props()
}

class SearchExecutor extends Actor {
  val youtubeSearch = context.actorOf(YoutubeSearch.props())
  val soundCloudSearch = context.actorOf(SoundCloudSearch.props())

  override def receive: Receive = {
    case Search(query) =>
      val ytQuery = youtubeSearch ? Search(query)
      val sqQuery = soundCloudSearch  ? Search(query)
      val (ytResult: Result, scResult: Result) = for {
        y <- ytQuery.mapTo[Result]
        s <- sqQuery.mapTo[Result]
      } yield (y, s)

      sender ! ytResult.data.zip(scResult.data)
  }
}
