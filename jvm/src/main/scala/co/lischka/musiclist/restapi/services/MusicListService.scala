package co.lischka.musiclist.restapi.services

import co.lischka.musiclist.restapi.models.{MusicListEntity, TrackAtListEntity, TrackEntity}
import co.lischka.musiclist.restapi.models.db.{MusicListEntityTable, TrackAtListEntityTable, TrackEntityTable}
import co.lischka.musiclist.restapi.utils.DatabaseService
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class MusicListService(val databaseService: DatabaseService, tracksService: TracksService)(implicit executionContext: ExecutionContext)
  extends MusicListEntityTable with TrackAtListEntityTable with TrackEntityTable {

  import tracksService._
  import databaseService._
  import databaseService.driver.api._

  def getLists(): Future[Seq[MusicListEntity]] = db.run(musicList.result)

  def getListById(id: Long): Future[Option[MusicListEntity]] = db.run(musicList.filter(_.id === id).result.headOption)

  def deleteList(id: Long): Future[Int] = db.run(musicList.filter(_.id === id).delete)

  def createList(muList: MusicListEntity): Future[MusicListEntity] = db.run(musicList returning musicList += muList)



  def updateList(listUpdate: MusicListEntity): Future[Option[MusicListEntity]] = {
    listUpdate.id match {
      case Some(id) => {
        val result = db.run(musicList.filter(_.id === listUpdate.id).update(listUpdate))
        // Result handling
        result.flatMap(nRowsAffected =>
          // Check if there's an alternative to get, it throws exceptions
          if (nRowsAffected <= 0) Future(None) else getListById(listUpdate.id.get)
        )
      }
      case None => Future(None)
    }
  }


  def getTracksAtList(permalink: String): Future[Seq[TrackEntity]] = {
    val join = for {
      ml <- musicList if ml.permalink === permalink
    
      //filter(_.musicListId === ml.id)

      // Currently does not filter by musiclist
      tAL <- trackAtList.map(_.trackId)
      tr <- tracks.filter(_.id === tAL)
    } yield tr
    return db.run(join.result)

  }


  def getMusicListByPermalink(permalink: String): Future[Option[MusicListEntity]] =
    db.run(musicList.filter(_.permalink === permalink).result.headOption)

  def getTrackAtListsByMusicListId(id: Long): Future[Seq[TrackAtListEntity]] =
    db.run(trackAtList.filter(_.musicListId === id).result)

}
