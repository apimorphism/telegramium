package telegramium.bots.client

/** @param description
  *   New bot description; 0-512 characters. Pass an empty string to remove the dedicated description for the given
  *   language.
  * @param languageCode
  *   A two-letter ISO 639-1 language code. If empty, the description will be applied to all users for whose language
  *   there is no dedicated description.
  */
final case class SetMyDescriptionReq(
  description: Option[String] = Option.empty,
  languageCode: Option[String] = Option.empty
)
