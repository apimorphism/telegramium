package telegramium.bots.client

/** @param name
  *   New bot name; 0-64 characters. Pass an empty string to remove the dedicated name for the given language.
  * @param languageCode
  *   A two-letter ISO 639-1 language code. If empty, the name will be shown to all users for whose language there is no
  *   dedicated name.
  */
final case class SetMyNameReq(name: Option[String] = Option.empty, languageCode: Option[String] = Option.empty)
