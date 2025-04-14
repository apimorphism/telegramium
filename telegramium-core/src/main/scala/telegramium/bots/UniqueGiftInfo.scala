package telegramium.bots

/** Describes a service message about a unique gift that was sent or received.
  *
  * @param gift
  *   Information about the gift
  * @param origin
  *   Origin of the gift. Currently, either “upgrade” or “transfer”
  * @param ownedGiftId
  *   Optional. Unique identifier of the received gift for the bot; only present for gifts received on behalf of
  *   business accounts
  * @param transferStarCount
  *   Optional. Number of Telegram Stars that must be paid to transfer the gift; omitted if the bot cannot transfer the
  *   gift
  */
final case class UniqueGiftInfo(
  gift: UniqueGift,
  origin: String,
  ownedGiftId: Option[String] = Option.empty,
  transferStarCount: Option[Int] = Option.empty
)
