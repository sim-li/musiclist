package co.lischka.musiclist.restapi.models

case class TrackEntity(id: Option[Long] = None, url: String, title: String, artist: String, description: String)