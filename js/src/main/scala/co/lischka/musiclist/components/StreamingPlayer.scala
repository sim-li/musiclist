package co.lischka.musiclist.components

import sri.core.{ReactElement, Component}
import sri.navigation.NavigationScreenComponent

import scala.scalajs.js.annotation.ScalaJSDefined
import sri.universal.components._
import scala.scalajs.js.Object

@ScalaJSDefined
class StreamingPlayer extends NavigationScreenComponent[StreamingPlayer.Params, StreamingPlayer.State] {
  import StreamingPlayer._
  initialState(State(currentTime=0))

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

//  @ScalaJSDefined
//  trait State extends Object {
//    val currentTime: Int
//  }


  case class State(currentTime: Int)
}