package co.lischka.musiclist.restapi.services


import co.lischka.musiclist.restapi.models.db.TrackAtListEntityTable
import co.lischka.musiclist.restapi.utils.DatabaseService

import scala.concurrent.ExecutionContext

class TrackAtListService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends TrackAtListEntityTable {
}