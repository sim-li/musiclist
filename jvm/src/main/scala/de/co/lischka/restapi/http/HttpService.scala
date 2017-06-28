package de.co.lischka.restapi.http

import akka.http.scaladsl.server.Directives._
import de.co.lischka.restapi.http.routes.{AuthServiceRoute, UsersServiceRoute}
import de.co.lischka.restapi.services.{AuthService, UsersService}
import de.co.lischka.restapi.utils.CorsSupport
import scala.concurrent.ExecutionContext

class HttpService(usersService: UsersService,
                  authService: AuthService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val usersRouter = new UsersServiceRoute(authService, usersService)
  val authRouter = new AuthServiceRoute(authService)

  val routes =
    pathPrefix("v1") {
      corsHandler {
        usersRouter.route ~
        authRouter.route
      }
    }

}
