import akka.actor.ActorSystem
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.SimpleRoutingApp

object Service extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  def persistToDatabase(data: String) = {
    println(s"$data")
  }

  lazy val putCustomerRoute = pathPrefix("persist") {
    (put & path(Segment)) { payload =>
      respondWithMediaType(MediaTypes.`application/json`) {
        persistToDatabase(payload)

        respondWithStatus(StatusCodes.OK) {
          complete(payload)
        }
      }
    }
  }

  startServer(interface = "localhost", port = 9090) {
    putCustomerRoute
  }
}
