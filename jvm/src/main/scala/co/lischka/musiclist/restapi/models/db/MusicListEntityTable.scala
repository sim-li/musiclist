package co.lischka.musiclist.restapi.models.db

import co.lischka.musiclist.restapi.utils.DatabaseService
import co.lischka.musiclist.restapi.models.MusicListEntity

trait MusicListEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  class MusicList(tag: Tag) extends Table[MusicListEntity](tag, "music_list"){
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def description = column[String]("description")
    def permalink = column[String]("permalink")

    def * = (id, title, description, permalink) <> ((MusicListEntity.apply _).tupled, MusicListEntity.unapply)
  }

  val musicList = TableQuery[MusicList]

}