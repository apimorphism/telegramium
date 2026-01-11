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
  * @param isPremium
  *   Optional. True, if the gift can only be purchased by Telegram Premium subscribers
  * @param hasColors
  *   Optional. True, if the gift can be used (after being upgraded) to customize a user's appearance
  * @param totalCount
  *   Optional. The total number of gifts of this type that can be sent by all users; for limited gifts only
  * @param remainingCount
  *   Optional. The number of remaining gifts of this type that can be sent by all users; for limited gifts only
  * @param personalTotalCount
  *   Optional. The total number of gifts of this type that can be sent by the bot; for limited gifts only
  * @param personalRemainingCount
  *   Optional. The number of remaining gifts of this type that can be sent by the bot; for limited gifts only
  * @param background
  *   Optional. Background of the gift
  * @param uniqueGiftVariantCount
  *   Optional. The total number of different unique gifts that can be obtained by upgrading the gift
  * @param publisherChat
  *   Optional. Information about the chat that published the gift
  */
final case class Gift(
  id: String,
  sticker: Sticker,
  starCount: Int,
  upgradeStarCount: Option[Int] = Option.empty,
  isPremium: Option[Boolean] = Option.empty,
  hasColors: Option[Boolean] = Option.empty,
  totalCount: Option[Int] = Option.empty,
  remainingCount: Option[Int] = Option.empty,
  personalTotalCount: Option[Int] = Option.empty,
  personalRemainingCount: Option[Int] = Option.empty,
  background: Option[GiftBackground] = Option.empty,
  uniqueGiftVariantCount: Option[Int] = Option.empty,
  publisherChat: Option[Chat] = Option.empty
)
