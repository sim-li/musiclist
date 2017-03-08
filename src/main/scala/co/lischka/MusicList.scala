package co.lischka

import scala.scalajs.js.JSApp
import org.scalajs.jquery.{jQuery => jQ}
import scalatags.Text.all._

case class Track(artist: String, title: String, img: String)

object MusicList extends JSApp {

  def trackTiles(trackList: List[Track]) = {
    val tilesPerRow = 3
    val tracksIntoRows = trackList.grouped(tilesPerRow).toList
      for (tracks <- tracksIntoRows) yield
      div(cls:="row",
          for (track <- tracks)
            yield div(cls:="col-sm3 col-md-2", div(cls := "thumbnail",
              img(src := track.img),
              div(cls:="caption",
              p(cls := "page-header",
                p(h3(track.artist)),
                p(h3(small(track.title)))
              ),
                a(cls:="btn btn-default", role:="button", "Play"),
                a(cls:="btn btn-default", role:="button", "Vid")
              )
            )
          )
      )
  }

  def main(): Unit = {
    println("Hello world")
    println(jQ("#jumbo"))
    jQ("#jumbo").empty()

    val trackList = List(
      Track(
        "Vanilla ice",
        "Ice, Ice, Baby",
        "http://illusion.scene360.com/wp-content/uploads/2014/10/computergraphics-album-covers-2014-15.jpg"
      ),
      Track(
        "Shakira",
        "I can see your body moving",
        "http://illusion.scene360.com/wp-content/uploads/2014/11/computergraphics-album-covers-2014-18.jpg"
      ),
      Track(
        "JayZ",
        "Shaking that thing",
        "http://illusion.scene360.com/wp-content/uploads/2014/10/computergraphics-album-covers-2014-03.jpg"
      ),
      Track(
        "Cool Dog",
        "Out hunting",
        "http://illusion.scene360.com/wp-content/uploads/2014/10/computergraphics-album-covers-2014-05.jpg"
      ),
      Track(
        "Manic Guy",
        "With style",
        "http://illusion.scene360.com/wp-content/uploads/2014/10/computergraphics-album-covers-2014-08.jpg"
      )
    )
    jQ("#jumbo").append(trackTiles(trackList).render)
  }
}
