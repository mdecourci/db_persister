package com.barclays

import akka.actor.ActorSystem
//import com.evaluate.load.GraphVertexLoader
import spray.http.{StatusCodes, MediaTypes}
import spray.routing.SimpleRoutingApp

object Service extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  def persistToDatabase(data: String) = {
    println(s"$data")
    //GraphVertexLoader.process(data)
  }

  lazy val persistDataRoute = put {
    path("persist") {
      entity(as[String]) { payload =>
        respondWithMediaType(MediaTypes.`application/json`) {

          persistToDatabase(payload)

          respondWithStatus(StatusCodes.OK) {
            complete(payload)
          }
        }
      }
    }
  }

  startServer(interface = "localhost", port = 9080) {
    persistDataRoute
  }
}
