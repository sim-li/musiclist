package co.lischka.musiclist.restapi.services

import co.lischka.musiclist.restapi.models.MusicListEntity
import co.lischka.musiclist.restapi.models.db.MusicListEntityTable
import co.lischka.musiclist.restapi.utils.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class MusicListService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends MusicListEntityTable{

  import databaseService._
  import databaseService.driver.api._

  def getList(): Future[Seq[MusicListEntity]] = db.run(musicList.result)

  def getListById(id: Long): Future[Option[MusicListEntity]] = db.run(musicList.filter(_.id === id).result.headOption)

  def deleteList(id: Long): Future[Int] = db.run(musicList.filter(_.id === id).delete)

  def createList(muList: MusicListEntity): Future[MusicListEntity] = db.run(musicList returning musicList += muList)

  //TODO Method updateList
}
