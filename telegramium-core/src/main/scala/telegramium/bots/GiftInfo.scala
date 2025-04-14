package telegramium.bots

/** Describes a service message about a regular gift that was sent or received.
  *
  * @param gift
  *   Information about the gift
  * @param ownedGiftId
  *   Optional. Unique identifier of the received gift for the bot; only present for gifts received on behalf of
  *   business accounts
  * @param convertStarCount
  *   Optional. Number of Telegram Stars that can be claimed by the receiver by converting the gift; omitted if
  *   conversion to Telegram Stars is impossible
  * @param prepaidUpgradeStarCount
  *   Optional. Number of Telegram Stars that were prepaid by the sender for the ability to upgrade the gift
  * @param canBeUpgraded
  *   Optional. True, if the gift can be upgraded to a unique gift
  * @param text
  *   Optional. Text of the message that was added to the gift
  * @param entities
  *   Optional. Special entities that appear in the text
  * @param isPrivate
  *   Optional. True, if the sender and gift text are shown only to the gift receiver; otherwise, everyone will be able
  *   to see them
  */
final case class GiftInfo(
  gift: Gift,
  ownedGiftId: Option[String] = Option.empty,
  convertStarCount: Option[Int] = Option.empty,
  prepaidUpgradeStarCount: Option[Int] = Option.empty,
  canBeUpgraded: Option[Boolean] = Option.empty,
  text: Option[String] = Option.empty,
  entities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  isPrivate: Option[Boolean] = Option.empty
)
