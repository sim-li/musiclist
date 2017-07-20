package co.lischka.musiclist.components

import co.lischka.musiclist.facades.player
import sri.core.Component
import sri.universal.components._
import scala.scalajs.js.Object
import scala.scalajs.js.annotation.ScalaJSDefined


//@ScalaJSDefined
//class PlayerScreen extends ComponentP[PlayerScreen.Props] {
//  def render() = {
//    Text(s"Hello ${props.name}")
//  }
//}
//
//object PlayerScreen {
//  case class Props(name : String)
//  def apply(props:Props) = CreateElement[PlayerScreen](props)
//}

@ScalaJSDefined
class PlayerScreen extends Component[PlayerScreen.Params, PlayerScreen.State] {
  import PlayerScreen._

  //jsSetState(State(currentTime=0))

  setState(State(currentTime=0))


  //jsSetState
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
          onPlay()

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