import cats.Applicative
import cats.effect.Sync
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.{HttpApp, HttpRoutes, Response}
import fs2.Stream
import fs2.text.utf8Encode

class AppBuilder[F[_] : Sync] {

  val helloWorldService = HttpRoutes.of[F] {
    case GET -> Root / "hello" / name =>
      Applicative[F].pure(Response[F](status = Ok, body = Stream(s"Hello $name").through(utf8Encode)))
  }

  val httpApp = Router("/" -> helloWorldService, "/api" -> helloWorldService).orNotFound

  def build(): HttpApp[F] = Router("/" -> helloWorldService, "/api" -> helloWorldService).orNotFound
}
