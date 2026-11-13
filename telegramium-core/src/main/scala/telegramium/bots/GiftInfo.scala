package telegramium.bots

/** Describes a service message about a regular gift that was sent or received.
  *
  * @param gift
  *   Information about the gift
  * @param ownedGiftId
  *   <em>Optional</em>. Unique identifier of the received gift for the bot; only present for gifts received on behalf
  *   of business accounts
  * @param convertStarCount
  *   <em>Optional</em>. Number of Telegram Stars that can be claimed by the receiver by converting the gift; omitted if
  *   conversion to Telegram Stars is impossible
  * @param prepaidUpgradeStarCount
  *   <em>Optional</em>. Number of Telegram Stars that were prepaid for the ability to upgrade the gift
  * @param isUpgradeSeparate
  *   <em>Optional</em>. <em>True</em>, if the gift's upgrade was purchased after the gift was sent
  * @param canBeUpgraded
  *   <em>Optional</em>. <em>True</em>, if the gift can be upgraded to a unique gift
  * @param text
  *   <em>Optional</em>. Text of the message that was added to the gift
  * @param entities
  *   <em>Optional</em>. Special entities that appear in the text
  * @param isPrivate
  *   <em>Optional</em>. <em>True</em>, if the sender and gift text are shown only to the gift receiver; otherwise,
  *   everyone will be able to see them
  * @param uniqueGiftNumber
  *   <em>Optional</em>. Unique number reserved for this gift when upgraded. See the <em>number</em> field in <a
  *   href="https://core.telegram.org/bots/api#uniquegift">UniqueGift</a>.
  */
final case class GiftInfo(
  gift: Gift,
  ownedGiftId: Option[String] = Option.empty,
  convertStarCount: Option[Int] = Option.empty,
  prepaidUpgradeStarCount: Option[Int] = Option.empty,
  isUpgradeSeparate: Option[Boolean] = Option.empty,
  canBeUpgraded: Option[Boolean] = Option.empty,
  text: Option[String] = Option.empty,
  entities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  isPrivate: Option[Boolean] = Option.empty,
  uniqueGiftNumber: Option[Int] = Option.empty
)
