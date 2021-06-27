package telegramium.bots.high

import telegramium.bots.client.Method

sealed trait BotApiError extends Throwable with Product with Serializable

final case class FailedRequest[A](
  method: Method[A],
  errorCode: Option[Int],
  description: Option[String]
) extends BotApiError {

  override def getMessage: String =
    s"method=${method.payload.name} code=${errorCode.map(_.toString).getOrElse("")} description=${description.getOrElse("")}"

}
