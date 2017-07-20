package co.lischka.musiclist.components

import co.lischka.musiclist.facades.{player, Player}
import sri.core.{ReactElement, Component}
import sri.navigation.NavigationScreenComponent
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import sri.universal.components._
import scala.scalajs.js.Object
import js.JSConverters._

@ScalaJSDefined
class StreamingPlayer extends NavigationScreenComponent[PlayerScreen.Params, PlayerScreen.State] {
  import PlayerScreen._

  //initialState(State(currentTime=0))


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
          Button(title = "Set New Params",
          onPress = () =>
            setParams(new Params {
              override val title: String = "New Title"
            }))

    )
  }
}

object StreamingPlayer {
  @ScalaJSDefined
  trait Params extends Object {
    val title: String
  }

  case class State(currentTime: Int)
}