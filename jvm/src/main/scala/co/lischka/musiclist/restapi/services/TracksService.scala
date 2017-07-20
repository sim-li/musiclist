package co.lischka.musiclist.restapi.services

import co.lischka.musiclist.restapi.models.{TrackEntity, UserEntity}
import co.lischka.musiclist.restapi.models.db.TrackEntityTable
import co.lischka.musiclist.restapi.utils.DatabaseService

import scala.concurrent.{ExecutionContext, Future}


class TracksService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends TrackEntityTable {

  import databaseService._
  import databaseService.driver.api._

  def getTracks(): Future[Seq[TrackEntity]] = db.run(tracks.result)

  def getTrackById(id: Long): Future[Option[TrackEntity]] = db.run(tracks.filter(_.id === id).result.headOption)

  def deleteTrack(id: Long): Future[Int] = db.run(tracks.filter(_.id === id).delete)

  def createTrack(track: TrackEntity): Future[TrackEntity] = db.run(tracks returning tracks += track)

  //TODO Method updateTrack

}