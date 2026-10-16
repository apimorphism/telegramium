package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param ownedGiftId
  *   Unique identifier of the regular gift that should be upgraded to a unique one
  * @param keepOriginalDetails
  *   Pass <em>True</em> to keep the original gift text, sender and receiver in the upgraded gift
  * @param starCount
  *   The amount of Telegram Stars that will be paid for the upgrade from the business account balance. If
  *   <code>gift.prepaid_upgrade_star_count &gt; 0</code>, then pass 0, otherwise, the <em>can_transfer_stars</em>
  *   business bot right is required and <code>gift.upgrade_star_count</code> must be passed.
  */
final case class UpgradeGiftReq(
  businessConnectionId: String,
  ownedGiftId: String,
  keepOriginalDetails: Option[Boolean] = Option.empty,
  starCount: Option[Int] = Option.empty
)
