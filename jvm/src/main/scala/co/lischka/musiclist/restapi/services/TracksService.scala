package co.lischka.musiclist.restapi.services

import co.lischka.musiclist.restapi.models.{MusicListEntity, TrackEntity, TrackMusicEntity, UserEntity}
import co.lischka.musiclist.restapi.models.db.{TrackEntityTable, TrackMusicEntityTable}
import co.lischka.musiclist.restapi.utils.DatabaseService
import co.lischka.musiclist.restapi.services.MusicListService

import scala.concurrent.{ExecutionContext, Future}


class TracksService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends TrackEntityTable with TrackMusicEntityTable {

  import databaseService._
  import databaseService.driver.api._

  def getTracks(): Future[Seq[TrackEntity]] = db.run(tracks.result)

  def getTrackById(id: Long): Future[Option[TrackEntity]] = db.run(tracks.filter(_.id === id).result.headOption)

  def deleteTrack(id: Long): Future[Int] = db.run(tracks.filter(_.id === id).delete)

  def createTrack(track: TrackEntity): Future[TrackEntity] = db.run(tracks returning tracks += track)

  def updateTrack(trackUpdate: TrackEntity): Future[Option[TrackEntity]] = {
    trackUpdate.id match {
      case Some(id) => {
        val result = db.run(tracks.filter(_.id === trackUpdate.id).update(trackUpdate))
        // Result handling
        result.flatMap(nRowsAffected =>
          if (nRowsAffected <= 0) Future(None) else getTrackById(trackUpdate.id.get)
        )
      }
      case None => Future(None)
    }
  }

  def createTrackAtList(trMuList: TrackMusicEntity): Future[TrackMusicEntity] = db.run(trackAtList returning trackAtList += trMuList)

  def insertTrackAtList(track: TrackEntity, musicListIds: Seq[Long]) = {
    createTrack(track) flatMap { track =>
      linkTracksWithMusicList(track.id.get, musicListIds)
    }
  }

  def linkTracksWithMusicList(musicListId: Long, trackIds: Seq[Long]) = {
    existAllTracks(trackIds) flatMap {
      case true =>
        db.run(trackAtList ++= trackIds.map((id: Long) => TrackMusicEntity(Some(musicListId), Some[Long](id))))
      case false => Future(None)
    }
  }

  def existAllTracks(tIds: Seq[Long]): Future[Boolean] = {
    val seqOfFutures = tIds map {
      existsTrack
    }
    Future.sequence(seqOfFutures) map { seq => seq.forall(_ == true) }
  }

  def existsTrack(tId: Long): Future[Boolean] = {
    db.run {
      tracks.filter(_.id === tId).length.result
    } map {
      _ == 1
    }
  }

  //def insertTrackAtList(track: TrackEntity, musiclist: MusicListEntity): Future[Option[TrackMusicEntity]] = {

  // Inspiration (probably not relevant, resolves Future[Option[...:
  // val someEntry = musicListService.getListById(5L) map {       <--- repl. with createTrack...
  // case Some(ml) => musicListService.updateList(ml.copy(permalink = "new_www.xyz.se"))
  // case None => print("Abc")
  // }

  // ...createTrack()....map ( linkTracksWithMusicList(musicList.id, Seq(_.id) ) //should resolve Future[

  // Create Track <- get back track id, put into Seq(trackId)
  // Search MusicList <- get MusicList id
  // linkTracksWithMusicList( ..., ... )

  //}
}