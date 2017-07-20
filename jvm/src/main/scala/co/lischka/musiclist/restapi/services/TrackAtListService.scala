package co.lischka.musiclist.restapi.services

import co.lischka.musiclist.restapi.models.TrackMusicEntity
import co.lischka.musiclist.restapi.models.db.TrackMusicEntityTable
import co.lischka.musiclist.restapi.utils.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class TrackAtListService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends TrackMusicEntityTable {

  import databaseService._
  import databaseService.driver.api._

  def getTrackAtList(): Future[Seq[TrackMusicEntity]] = db.run(trackAtList.result)

}
