package telegramium.bots

sealed trait OwnedGift {}

/** Describes a unique gift received and owned by a user or a chat.
  *
  * @param gift
  *   Information about the unique gift
  * @param sendDate
  *   Date the gift was sent in Unix time
  * @param ownedGiftId
  *   <em>Optional</em>. Unique identifier of the received gift for the bot; for gifts received on behalf of business
  *   accounts only
  * @param senderUser
  *   <em>Optional</em>. Sender of the gift if it is a known user
  * @param isSaved
  *   <em>Optional</em>. <em>True</em>, if the gift is displayed on the account's profile page; for gifts received on
  *   behalf of business accounts only
  * @param canBeTransferred
  *   <em>Optional</em>. <em>True</em>, if the gift can be transferred to another owner; for gifts received on behalf of
  *   business accounts only
  * @param transferStarCount
  *   <em>Optional</em>. Number of Telegram Stars that must be paid to transfer the gift; omitted if the bot cannot
  *   transfer the gift
  * @param nextTransferDate
  *   <em>Optional</em>. Point in time (Unix timestamp) when the gift can be transferred. If it is in the past, then the
  *   gift can be transferred now.
  */
final case class OwnedGiftUnique(
  gift: UniqueGift,
  sendDate: Long,
  ownedGiftId: Option[String] = Option.empty,
  senderUser: Option[User] = Option.empty,
  isSaved: Option[Boolean] = Option.empty,
  canBeTransferred: Option[Boolean] = Option.empty,
  transferStarCount: Option[Int] = Option.empty,
  nextTransferDate: Option[Long] = Option.empty
) extends OwnedGift

/** Describes a regular gift owned by a user or a chat.
  *
  * @param gift
  *   Information about the regular gift
  * @param sendDate
  *   Date the gift was sent in Unix time
  * @param ownedGiftId
  *   <em>Optional</em>. Unique identifier of the gift for the bot; for gifts received on behalf of business accounts
  *   only
  * @param senderUser
  *   <em>Optional</em>. Sender of the gift if it is a known user
  * @param text
  *   <em>Optional</em>. Text of the message that was added to the gift
  * @param entities
  *   <em>Optional</em>. Special entities that appear in the text
  * @param isPrivate
  *   <em>Optional</em>. <em>True</em>, if the sender and gift text are shown only to the gift receiver; otherwise,
  *   everyone will be able to see them
  * @param isSaved
  *   <em>Optional</em>. <em>True</em>, if the gift is displayed on the account's profile page; for gifts received on
  *   behalf of business accounts only
  * @param canBeUpgraded
  *   <em>Optional</em>. <em>True</em>, if the gift can be upgraded to a unique gift; for gifts received on behalf of
  *   business accounts only
  * @param wasRefunded
  *   <em>Optional</em>. <em>True</em>, if the gift was refunded and isn't available anymore
  * @param convertStarCount
  *   <em>Optional</em>. Number of Telegram Stars that can be claimed by the receiver instead of the gift; omitted if
  *   the gift cannot be converted to Telegram Stars; for gifts received on behalf of business accounts only
  * @param prepaidUpgradeStarCount
  *   <em>Optional</em>. Number of Telegram Stars that were paid for the ability to upgrade the gift
  * @param isUpgradeSeparate
  *   <em>Optional</em>. <em>True</em>, if the gift's upgrade was purchased after the gift was sent; for gifts received
  *   on behalf of business accounts only
  * @param uniqueGiftNumber
  *   <em>Optional</em>. Unique number reserved for this gift when upgraded. See the <em>number</em> field in <a
  *   href="https://core.telegram.org/bots/api#uniquegift">UniqueGift</a>.
  */
final case class OwnedGiftRegular(
  gift: Gift,
  sendDate: Long,
  ownedGiftId: Option[String] = Option.empty,
  senderUser: Option[User] = Option.empty,
  text: Option[String] = Option.empty,
  entities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  isPrivate: Option[Boolean] = Option.empty,
  isSaved: Option[Boolean] = Option.empty,
  canBeUpgraded: Option[Boolean] = Option.empty,
  wasRefunded: Option[Boolean] = Option.empty,
  convertStarCount: Option[Int] = Option.empty,
  prepaidUpgradeStarCount: Option[Int] = Option.empty,
  isUpgradeSeparate: Option[Boolean] = Option.empty,
  uniqueGiftNumber: Option[Int] = Option.empty
) extends OwnedGift
