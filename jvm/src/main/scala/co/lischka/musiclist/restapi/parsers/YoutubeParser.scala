package co.lischka.musiclist.restapi.parsers

import akka.http.scaladsl.model.headers.LinkParams.title
import co.lischka.musiclist.restapi.models.{TrackEntity, UserEntity}
import io.circe.parser._
import io.circe.optics.JsonPath._
import io.circe.optics.JsonPath._



object YoutubeParser {
  def parseTracks(inp: String): List[TrackEntity]  = {
    UserEntity(username="abc", password="def")

    println("Input is")
    println(inp)
    parse(inp) match {
      case Left(failure) => List()
      case Right(json) => {

        // videoId: items[0].id.videoId; items[0].id.kind=="youtube#video"
        // title
        // .description

        // val _phoneNum = root.order.customer.contactDetails.phone.string
        // _phoneNum: monocle.Optional[io.circe.Json,String] = monocle.POptional$$anon$1@129eba5a

        //val phoneNum: Option[String] = _phoneNum.getOption(json)

        val _id = root.items.index(0).id.videoId.string
        val id: Option[String] = _id.getOption(json)

        List(TrackEntity(
          url=s"http://test.de/${id}",
          title="My cool title",
          description="A description"
        ))

      }
    }
  }
}
