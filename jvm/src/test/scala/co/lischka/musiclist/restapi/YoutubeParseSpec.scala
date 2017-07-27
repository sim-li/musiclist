//package co.lischka.musiclist.restapi
//
//import co.lischka.musiclist.restapi.parsers.YoutubeParser
//import org.scalatest.FlatSpec
//
//class YoutubeParseSpec extends FlatSpec{
//    "A YoutubeParser" should "parse a title" in {
//        //Valid I
//        val inp = s"""
//        {
//            "items": [
//            {
//                "kind": "youtube#searchResult",
//                "id": {
//                    "kind": "youtube#video",
//                    "videoId": "fNr8kqSLpxQ"
//                }
//            },
//            {
//                "kind": "youtube#searchResult",
//                "id": {
//                    "kind": "youtube#video",
//                    "videoId": "b6hoBp7Hk-A"
//                },
//                "snippet": {
//                    "publishedAt": "2014-11-12T18:00:17.000Z",
//                    "channelId": "UC3Yc0vyFkYXB1_njh3uj7yw",
//                    "title": "World's best surfing 2014/2015 (HD)",
//                    "description": "Subscribe: http://bit.ly/SUBICTV Submit a Video : http://bit.ly/T1RsJh Facebook: https://www.facebook.com/Icompilationtv Follow us on Twitter ...",
//                }
//            }
//        """
//        val result = YoutubeParser.parseTracks(inp)
//        assert(result(0). === 1)
//    }
//}
