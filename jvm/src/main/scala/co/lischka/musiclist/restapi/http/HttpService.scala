package co.lischka.musiclist.restapi.http

import akka.http.scaladsl.server.Directives._
import co.lischka.musiclist.restapi.http.routes.{AuthServiceRoute, UsersServiceRoute, SearchServiceRoute}
import co.lischka.musiclist.restapi.services.{AuthService, UsersService}
import co.lischka.musiclist.restapi.utils.CorsSupport
import scala.concurrent.ExecutionContext

class HttpService(usersService: UsersService,
                  authService: AuthService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val usersRouter = new UsersServiceRoute(authService, usersService)
  val authRouter = new AuthServiceRoute(authService)
  val searchRouter = new SearchServiceRoute()

  val routes =
    pathPrefix("v1") {
      corsHandler {
        usersRouter.route ~
        authRouter.route ~
        searchRouter.route
      }
    }

}
