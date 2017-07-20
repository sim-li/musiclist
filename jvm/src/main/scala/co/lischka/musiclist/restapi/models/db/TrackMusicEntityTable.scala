/*package co.lischka.musiclist.restapi.models.db

import co.lischka.musiclist.restapi.models.TrackMusicEntity
import co.lischka.musiclist.restapi.utils.DatabaseService

trait TrackMusicEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  class Permalink(tag: Tag) extends Table[TrackMusicEntity](tag, "permalink") {
    def trackId = column[Long]("track_Id")
    def musicListId = column[Long]("musicList_Id")

    def * = (trackId, musicListId) <> ((TrackMusicEntity.apply _).tupled, TrackMusicEntity.unapply)
    def pk = primaryKey("primaryKey", (trackId, musicListId))

    def trackFK = foreignKey("fk_track", trackId, TableQuery[Track])(track => track.id, onDelete = ForeignKeyAction.Cascade)
    def musicListFK = foreignKey("fk_musicList", musicListId, TableQuery[MusicList])(list => list.id)
  }

  val permalink = TableQuery[Permalink]
}
*/