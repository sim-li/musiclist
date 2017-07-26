package co.lischka.musiclist.restapi.http

import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import co.lischka.musiclist.restapi.http.routes.{AuthServiceRoute, SearchServiceRoute, UsersServiceRoute, TracksServiceRoute, MusicListServiceRoute}
import co.lischka.musiclist.restapi.services._
import co.lischka.musiclist.restapi.utils.CorsSupport

import scala.concurrent.ExecutionContext

class HttpService(usersService: UsersService,
                  authService: AuthService,
                  searchService: YoutubeSearchService,
                  tracksService: TracksService,
                  musicListService: MusicListService,
                  trackAtListService: TrackAtListService
                 )(implicit executionContext: ExecutionContext,
                   system: akka.actor.ActorSystem,
                   materializer: ActorMaterializer
                 ) extends CorsSupport {

  val usersRouter = new UsersServiceRoute(authService, usersService)
  val authRouter = new AuthServiceRoute(authService)
  val searchRouter = new SearchServiceRoute(searchService)
  val trackRouter = new TracksServiceRoute(tracksService)
  val musicListRouter = new MusicListServiceRoute(musicListService, tracksService)

  val routes =
    pathPrefix("v1") {
      corsHandler {
        usersRouter.route ~
          authRouter.route ~
          searchRouter.route ~
          trackRouter.route ~
          musicListRouter.route
      }
    }
}
