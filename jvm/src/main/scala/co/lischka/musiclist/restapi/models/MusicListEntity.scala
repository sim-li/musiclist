package co.lischka.musiclist.restapi.models

case class MusicListEntity(id: Option[Long] = None, title: String, description: String, permalink: String)
