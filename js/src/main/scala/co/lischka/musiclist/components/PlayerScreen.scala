package co.lischka.musiclist.components

import co.lischka.musiclist.facades.player
import sri.core.Component
import sri.navigation.NavigationScreenComponent
import sri.universal.components._
import scala.scalajs.js
import scala.scalajs.js.Object
import scala.scalajs.js.annotation.ScalaJSDefined
import js.JSConverters._


//https://github.com/scalajs-react-interface/sri/blob/master/docs/DefiningComponents.md
@ScalaJSDefined
class PlayerScreen extends NavigationScreenComponent[PlayerScreen.Params, PlayerScreen.State] {
  import PlayerScreen.State

  initialState(State(currentTime=0))

  def onPlay(): Unit = {
    player.play("http://pianosolo.streamguys.net/live.m3u", Map(
      "title" -> "Aaron",
      "artist" -> "Celine Dion",
      "album_art_uri" -> "https://unsplash.it/300/300"
    ).toJSDictionary)
  }

  def onPause(): Unit = {
    player.pause()
  }

  override def render() = {
    View(style = GlobalStyles.wholeContainer)(
      Text("Secreen loaded"),
      Button(title = "Play", onPress = () => {
        print("Calling pause")
        player.pause()
//        player.play("http://pianosolo.streamguys.net/live.m3u", Map(
//          "title" -> "Aaron",
//          "artist" -> "Celine Dion",
//          "album_art_uri" -> "https://unsplash.it/300/300"
//        ).toJSDictionary)
      }

      )
    )
  }
}

object PlayerScreen {
  @ScalaJSDefined
  trait Params extends Object {
    val title: String
  }

  case class State(currentTime: Int)
}