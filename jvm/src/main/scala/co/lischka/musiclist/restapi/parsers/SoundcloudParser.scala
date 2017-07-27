package co.lischka.musiclist.restapi.parsers

import akka.http.scaladsl.model.headers.LinkParams.title
import co.lischka.musiclist.restapi.models.{TrackEntity, UserEntity}
import io.circe.parser._
import io.circe.optics.JsonPath._
import cats.syntax.either._
import co.lischka.musiclist.restapi.Main.doc
import io.circe._
import io.circe.parser._



object SoundcloudParser {
  def parseTracks(json: String) = {

    parse(json) match {
      case Left(failure) => List()
      case Right(json) => {

        val cursor: HCursor = doc.hcursor


        val urlres: Decoder.Result[String] =
          cursor.downField("uri").as[String]

        val titleres: Decoder.Result[String] =
          cursor.downField("title").as[String]

        val artistres: Decoder.Result[String] =
          cursor.downField("title").as[String]

        val descriptionres:  Decoder.Result[String] =
          cursor.downField("description").as[String]





      }
    }
  }
}
