package co.lischka.musiclist.restapi.models.db

import co.lischka.musiclist.restapi.models.TrackMusicEntity
import co.lischka.musiclist.restapi.utils.DatabaseService
import co.lischka.musiclist.restapi.services.{MusicListService, TracksService}

trait TrackMusicEntityTable extends TrackEntityTable with MusicListEntityTable{

  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  class TrackAtList(tag: Tag) extends Table[TrackMusicEntity](tag, "track_at_list") {
    def trackId = column[Option[Long]]("track_Id")
    def musicListId = column[Option[Long]]("musicList_Id")

    def * = (trackId, musicListId) <> ((TrackMusicEntity.apply _).tupled, TrackMusicEntity.unapply)
    def pk = primaryKey("primaryKey", (trackId, musicListId))

    def trackFK = foreignKey("fk_track", trackId, tracks)(_.id, onDelete = ForeignKeyAction.Cascade)
    def musicListFK = foreignKey("fk_musicList", musicListId, musicList)(_.id)
  }

  val trackAtList = TableQuery[TrackAtList]

}
