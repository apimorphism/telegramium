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

final case class ResponseDecodingError(message: String, cause: Option[Throwable])
    extends Throwable(message, cause.orNull)

object ResponseDecodingError {

  def default(details: String, cause: Option[Throwable]): ResponseDecodingError =
    ResponseDecodingError(
      s"""Cannot decode Telegram Bot API response:
         |$details
         |
         |Your Telegramium version might not be compatible with the current Telegram Bot API version, try updating it.
         |""".stripMargin,
      cause
    )

}
