package co.lischka.musiclist.restapi.search

import akka.actor.{Actor, Props}
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.search.SearchMessages.{Search, Result}
import akka.pattern.ask
import scala.concurrent.{Future, ExecutionContext}
import akka.event.Logging


object SearchExecutor {
  def props()(implicit timeout: akka.util.Timeout
  ) = Props(new SearchExecutor())
}

class SearchExecutor()(implicit timeout: akka.util.Timeout
) extends Actor {
  import context._

  val log = Logging(context.system, this)

  val youtubeSearch = context.actorOf(YoutubeSearch.props)
  val soundCloudSearch = context.actorOf(SoundCloudSearch.props)

  override def receive: Receive = {

    case Search(query) => {
      val routeModule = sender()

      val ytQuery = youtubeSearch ? Search(query)
      val scQuery = soundCloudSearch ? Search(query)

      val composedQuery: Future[List[TrackEntity]] = for {
        y <- ytQuery.mapTo[Result]
        s <- scQuery.mapTo[Result]
      } yield y.data zip s.data flatMap (d => List(d._1, d._2))

      composedQuery.foreach(routeModule ! Result(_))
    }
  }
}
