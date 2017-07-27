package co.lischka.musiclist


import co.lischka.musiclist.restapi.models.TrackEntity
import io.circe.{Decoder, HCursor, Json}
//import io.circe.parser._
import org.scalatest.FlatSpec
import cats.syntax.either._
//import io.circe._, io.circe.parser._
//import io.circe.parser.decode
//import io.circe.syntax._
import cats.syntax.either._
import io.circe._, io.circe.parser._

class ParsingExplore extends FlatSpec {

  "some exploration" should "be dandy" in {
    val json: String =
      """
[
  {
         "id": 230155983,
         "kind": "track",
         "created_at": "2015/10/26 14:51:09 +0000",
         "last_modified": "2017/07/09 12:01:30 +0000",
         "permalink": "hello-adele",
         "permalink_url": "https://soundcloud.com/gracedaviesofficial/hello-adele",
         "title": "Hello - Adele",
         "duration": 300880,
         "sharing": "public",
         "waveform_url": "https://w1.sndcdn.com/4axwr3aUKixa_m.png",
         "stream_url": "https://api.soundcloud.com/tracks/230155983/stream",
         "likes_count": 750804,
         "attachments_uri": "https://api.soundcloud.com/tracks/230155983/attachments",
         "bpm": null,
         "key_signature": "",
         "user_favorite": false,
         "uri": "https://api.soundcloud.com/tracks/230155983",
         "user_playback_count": null,
         "video_url": null,
         "download_url": null,
         "policy": "ALLOW",
         "monetization_model": "NOT_APPLICABLE"
     },
     {
        "id": 2301559,
          "kind": "track",
          "created_at": "2015/10/26 14:51:09 +0000",
          "last_modified": "2017/07/09 12:01:30 +0000",
          "permalink": "hello-adele",
          "permalink_url": "https://soundcloud.com/gracedaviesofficial/hello-adele",
          "title": "XXXX",
          "duration": 300880,
          "sharing": "public",
          "waveform_url": "https://w1.sndcdn.com/4axwr3aUKixa_m.png",
          "stream_url": "https://api.soundcloud.com/tracks/230155983/stream",
          "likes_count": 750804,
          "attachments_uri": "https://api.soundcloud.com/tracks/230155983/attachments",
          "bpm": null,
          "key_signature": "",
          "user_favorite": false,
          "uri": "https://apfdasfasfascks/230155983",
          "user_playback_count": null,
          "video_url": null,
          "download_url": null,
          "policy": "ALLOW",
          "monetization_model": "NOT_APPLICABLE"
     }
]
      """

    val temp = json.replaceAll("\n", "").split(",")
    val titles = temp.filter(_.matches(".*title.*")).map(_.split(":")(1).split("\"")(1))
    val uri = temp.filter(_.matches(".*\"uri.*")).map(_.split("\":")(1).split("\"")(1))
    val titlesAndUri = titles.zip(uri)
    val result = titlesAndUri.map(t => TrackEntity(title = t._1, url=t._2 , artist="", description=""))

    print("")
  }
}
