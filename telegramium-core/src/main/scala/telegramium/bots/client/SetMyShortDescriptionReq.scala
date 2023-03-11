package telegramium.bots.client

/** @param shortDescription
  *   New short description for the bot; 0-120 characters. Pass an empty string to remove the dedicated short
  *   description for the given language.
  * @param languageCode
  *   A two-letter ISO 639-1 language code. If empty, the short description will be applied to all users for whose
  *   language there is no dedicated short description.
  */
final case class SetMyShortDescriptionReq(
  shortDescription: Option[String] = Option.empty,
  languageCode: Option[String] = Option.empty
)
