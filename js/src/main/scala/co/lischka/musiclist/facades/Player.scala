package co.lischka.musiclist.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSGlobal}
import scala.scalajs.js.annotation.JSImport.Namespace

//"react-native-streaming-audio-player

@JSImport("react-native-streaming-audio-player", Namespace)
@js.native
object player extends Player

@js.native
trait Player extends js.Object {

  def play(streamUrl: String, metadata: js.Dictionary[String]): Unit = js.native

  def pause(): Unit = js.native

  def resume(): Unit = js.native

  def stop(): Unit = js.native

  def seekTo(timeMillis: Int): Unit = js.native


  // These methods are not present in IOS version, maybe should be implemented
  //
  //  def isPlaying(Callback cb) {
  //    cb.invoke(mService.getPlayback().isPlaying());
  //  }
  //
  //  @ReactMethod
  //  public void getDuration(Callback cb) {
  //    cb.invoke(mService.getPlayback().getDuration());
  //  }
  //
  //  @ReactMethod
  //  public void getCurrentPosition(Callback cb) {
  //    cb.invoke(mService.getPlayback().getCurrentPosition());
  //  }

}
