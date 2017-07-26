package co.lischka.musiclist.restapi.models

// TODO: Add artist field
case class TrackEntity(id: Option[Long] = None, url: String, title: String, description: String)