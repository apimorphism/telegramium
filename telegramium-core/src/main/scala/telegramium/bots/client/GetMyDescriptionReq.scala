package telegramium.bots.client

/** @param languageCode A two-letter ISO 639-1 language code or an empty string */
final case class GetMyDescriptionReq(languageCode: Option[String] = Option.empty)
