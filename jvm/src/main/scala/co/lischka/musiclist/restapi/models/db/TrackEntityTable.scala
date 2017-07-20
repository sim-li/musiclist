package co.lischka.musiclist.restapi.models.db

import co.lischka.musiclist.restapi.Main.databaseService
import co.lischka.musiclist.restapi.utils.DatabaseService
import co.lischka.musiclist.restapi.models.TrackEntity

trait TrackEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  class Track(tag: Tag) extends Table[TrackEntity](tag, "track"){
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def url = column[String]("url")
    def title = column[String]("title")
    def description = column[String]("description")

    def * = (id, url, title, description) <> ((TrackEntity.apply _).tupled, TrackEntity.unapply)
  }

  protected val track = TableQuery[Track]
  def cretb(){
    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      (track.schema).create)
    val fin = db.run(setup)
  }
}