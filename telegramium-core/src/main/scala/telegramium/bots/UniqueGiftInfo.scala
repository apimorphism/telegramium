package telegramium.bots

/** Describes a service message about a unique gift that was sent or received.
  *
  * @param gift
  *   Information about the gift
  * @param origin
  *   Origin of the gift. Currently, either “upgrade” for gifts upgraded from regular gifts, “transfer” for gifts
  *   transferred from other users or channels, “resale” for gifts bought from other users, “gifted_upgrade” for
  *   upgrades purchased after the gift was sent, or “offer” for gifts bought or sold through gift purchase offers.
  * @param text
  *   Optional. Text of the message that was added to the gift
  * @param entities
  *   Optional. Special entities that appear in the text
  * @param isPrivate
  *   Optional. True, if the sender and gift text are shown only to the gift receiver; otherwise, everyone will be able
  *   to see them
  * @param lastResaleCurrency
  *   Optional. For gifts bought from other users, the currency in which the payment for the gift was done. Currently,
  *   one of “XTR” for Telegram Stars or “TON” for TON grams.
  * @param lastResaleAmount
  *   Optional. For gifts bought from other users, the price paid for the gift in either Telegram Stars or nanograms
  * @param ownedGiftId
  *   Optional. Unique identifier of the received gift for the bot; only present for gifts received on behalf of
  *   business accounts
  * @param transferStarCount
  *   Optional. Number of Telegram Stars that must be paid to transfer the gift; omitted if the bot cannot transfer the
  *   gift
  * @param nextTransferDate
  *   Optional. Point in time (Unix timestamp) when the gift can be transferred. If it is in the past, then the gift can
  *   be transferred now.
  */
final case class UniqueGiftInfo(
  gift: UniqueGift,
  origin: String,
  text: Option[String] = Option.empty,
  entities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  isPrivate: Option[Boolean] = Option.empty,
  lastResaleCurrency: Option[String] = Option.empty,
  lastResaleAmount: Option[Long] = Option.empty,
  ownedGiftId: Option[String] = Option.empty,
  transferStarCount: Option[Int] = Option.empty,
  nextTransferDate: Option[Long] = Option.empty
)
