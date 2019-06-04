package telegramium.bots

import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import cats.effect._
import CirceImplicits._

trait WebhookServiceHttp4sBackend[F[_]] {

  import cats.syntax.functor._
  import cats.syntax.flatMap._

  implicit def syncF: Sync[F]

  /***/
  def handleUpdate(x: HandleUpdateReq): F[HandleUpdateRes] = ???

  implicit val HandleUpdateReqEntityDecoder: EntityDecoder[F, HandleUpdateReq] =
    jsonOf[F, HandleUpdateReq]

  def handleUpdateReq(rawReq: org.http4s.Request[F]): F[org.http4s.Response[F]] = {
    for {
      req <- rawReq.as[HandleUpdateReq]
      res <- handleUpdate(req)
    } yield {
      org.http4s.Response().withEntity(res.asJson)
    }
  }

}
