package co.lischka.musiclist


import co.lischka.musiclist.restapi.models.{YoutubeTrack, Track, MusicList}
import org.scalatest.FlatSpec

class TrackSpec extends FlatSpec{

  "" should "" in {
      MusicList(permaLink="XJIAOSAIO", tracks=List[Track](
        YoutubeTrack(url="http://xyzde", title="my title", description="my description")
      ))
  }
}
