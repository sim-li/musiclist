package co.lischka.musiclist.restapi.models.db

import co.lischka.musiclist.restapi.models.TrackMusicEntity
import co.lischka.musiclist.restapi.utils.DatabaseService
import co.lischka.musiclist.restapi.services.{MusicListService, TracksService}

trait TrackMusicEntityTable extends TrackEntityTable {

  //protected val tracksService: TracksService
  //protected val musicListService: MusicListService
  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  class TrackAtList(tag: Tag) extends Table[TrackMusicEntity](tag, "track_at_list") {
    def trackId = column[Long]("track_Id")
    def musicListId= column[Long]("musicList_Id")

    def * = (trackId, musicListId) <> ((TrackMusicEntity.apply _).tupled, TrackMusicEntity.unapply)
    def pk = primaryKey("primaryKey", (trackId, musicListId))

    /*def trackFK = foreignKey("fk_track", trackId, tracksService.tracks)(track => track.id, onDelete = ForeignKeyAction.Cascade)
    def musicListFK = foreignKey("fk_musicList", musicListId, musicListService.musicList)(musiclist => musiclist.id)*/
    /*def trackFK = foreignKey("fk_track", trackId, tracks)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )*/
  }

  val trackAtList = TableQuery[TrackAtList]
}
