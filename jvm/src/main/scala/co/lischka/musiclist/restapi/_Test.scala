/*val u = new UserEntity(username = "test", password = "test")
authService.signUp(u)
val t = new TrackEntity(url = "www", title = "title", description = "pop")
tracksService.createTrack(t)


// Save list from database
//val l = new MusicListEntity(permalink = "www.xyz.se")
//musicListService.createList(l)

val someEntry = musicListService.getListById(3L) map {
  case Some(ml) => musicListService.updateList(ml.copy(permalink = "1new_www.xyz.se"))
  case None => print("error")
}

val muli = new MusicListEntity(permalink = "www.abc.se")
val cremuli1 = new TrackEntity(url = "www", title = "title", description = "rock")
val me = new TrackAtListEntity(Some(5L), Some(3L))
tracksService.createTrackAtList(me)
tracksService.deleteTrack(1L)

val list1 = new MusicListEntity(permalink = "www.com")
val list2 = new MusicListEntity(permalink = "www.com.de")
val cremuli = new TrackEntity(url = "www", title = "title", description = "jazz")
val cremul = new TrackEntity(url = "www", title = "title", description = "house")
tracksService.insertTrackAtList(cremuli, Seq(3L))
tracksService.insertTrackAtList(cremul, Seq(3L))
musicListService.insertListAtTrack(list1, Seq(10L))
musicListService.insertListAtTrack(list2, Seq(10L))*/
