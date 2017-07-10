package co.lischka.musiclist.components

import sri.navigation._
import sri.universal.components._
import scala.scalajs.js.Object
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class ScreenWithParams
    extends NavigationScreenComponentP[ScreenWithParams.Params] {
  import ScreenWithParams._
  def render() = {
    View(style = GlobalStyles.wholeContainer)(
      Button(title = "Set New Params",
             onPress = () =>
               setParams(new Params {
                 override val title: String = "New Title"
               }))
    )
  }
}

object ScreenWithParams {
  @ScalaJSDefined
  trait Params extends Object {
    val title: String
  }
}
