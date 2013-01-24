import play.api._
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {

  // called when a route is found, but it was not possible to bind the request parameters
  override def onBadRequest(request: RequestHeader, error: String) = {
    BadRequest("Bad Request: " + error)
  } 

  // 500 - internal server error
  override def onError(request: RequestHeader, throwable: Throwable) = {
    InternalServerError(views.html.errors.onError(throwable))
  }
  // 404 - page not found error
  override def onHandlerNotFound(request: RequestHeader): Result = {
    NotFound(views.html.errors.onHandlerNotFound(request))
  }

}