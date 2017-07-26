package co.lischka.musiclist.restapi.search

import co.lischka.musiclist.restapi.models.TrackEntity

object SearchMessages {
  case class Search(query: String)

  case class Result(data: List[TrackEntity])
}
