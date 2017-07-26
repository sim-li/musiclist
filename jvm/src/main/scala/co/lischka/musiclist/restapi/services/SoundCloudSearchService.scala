package co.lischka.musiclist.restapi.services

import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.models.TrackEntity
import co.lischka.musiclist.restapi.parsers.YoutubeParser

import scala.concurrent.{ExecutionContext, Future}

class SoundCloudSearchService() (
  implicit executionContext: ExecutionContext, system: akka.actor.ActorSystem, materializer: ActorMaterializer
) extends SearchService {

  def search(title: String): Future[List[TrackEntity]] = {
    //Target:
    //  - Search Result with
    //      List of TrackIds
    //
    // Links have to look like this:
    // https://soundcloud.com/deniro_skemez/deniro-skemez-goin-off-prelude
    //
    val soundCloudUri = s"xyz${title}"

    httpRequest(soundCloudUri).map(print _)

    //TODO: For dev, remove
    Future(List())
  }
}
