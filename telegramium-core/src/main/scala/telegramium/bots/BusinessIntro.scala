package telegramium.bots

/** Contains information about the start page settings of a Telegram Business account.
  *
  * @param title
  *   <em>Optional</em>. Title text of the business intro
  * @param message
  *   <em>Optional</em>. Message text of the business intro
  * @param sticker
  *   <em>Optional</em>. Sticker of the business intro
  */
final case class BusinessIntro(
  title: Option[String] = Option.empty,
  message: Option[String] = Option.empty,
  sticker: Option[Sticker] = Option.empty
)
