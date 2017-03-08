package co.lischka

import scala.scalajs.js.JSApp
import scalatags.JsDom.all._
import org.scalajs.jquery.{jQuery => jQ}

object MusicList extends JSApp {
  def abc() = {
    div(cls:="jumbotron",
      div(cls:="thumbnail",
        img(src:="http://illusion.scene360.com/wp-content/uploads/2014/10/computergraphics-album-covers-2014-15.jpg"),
        p(cls:="page-header",
          p(h3("Vanilla ice")),
          p(h3(small("Ice, Ice, Baby")))
        )
      )
    )
  }

  def main(): Unit = {
    println("Hello world")
    jQ("#jumbo").append(abc.render)
  }
}
