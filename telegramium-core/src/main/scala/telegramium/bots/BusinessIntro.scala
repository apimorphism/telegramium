package telegramium.bots

/** @param title
  *   Optional. Title text of the business intro
  * @param message
  *   Optional. Message text of the business intro
  * @param sticker
  *   Optional. Sticker of the business intro
  */
final case class BusinessIntro(
  title: Option[String] = Option.empty,
  message: Option[String] = Option.empty,
  sticker: Option[Sticker] = Option.empty
)
