package telegramium.bots.high

final case class ResponseDecodingError(message: String, cause: Option[Throwable])
    extends Throwable(message, cause.orNull)
