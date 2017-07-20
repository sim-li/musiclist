package co.lischka.musiclist.restapi.models.db

import co.lischka.musiclist.restapi.utils.DatabaseService
import co.lischka.musiclist.restapi.models.MusicListEntity

trait MusicListEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  class MusicList(tag: Tag) extends Table[MusicListEntity](tag, "musicList"){
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def permalink = column[String]("permalink")

    def * = (id, permalink) <> ((MusicListEntity.apply _).tupled, MusicListEntity.unapply)
  }

  val musicList = TableQuery[MusicList]

}