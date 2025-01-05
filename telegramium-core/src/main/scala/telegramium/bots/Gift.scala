package telegramium.bots

/** This object represents a gift that can be sent by the bot.
  *
  * @param id
  *   Unique identifier of the gift
  * @param sticker
  *   The sticker that represents the gift
  * @param starCount
  *   The number of Telegram Stars that must be paid to send the sticker
  * @param upgradeStarCount
  *   Optional. The number of Telegram Stars that must be paid to upgrade the gift to a unique one
  * @param totalCount
  *   Optional. The total number of the gifts of this type that can be sent; for limited gifts only
  * @param remainingCount
  *   Optional. The number of remaining gifts of this type that can be sent; for limited gifts only
  */
final case class Gift(
  id: String,
  sticker: Sticker,
  starCount: Int,
  upgradeStarCount: Option[Int] = Option.empty,
  totalCount: Option[Int] = Option.empty,
  remainingCount: Option[Int] = Option.empty
)
