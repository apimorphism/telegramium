package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param ownedGiftId
  *   Unique identifier of the regular gift that should be transferred
  * @param newOwnerChatId
  *   Unique identifier of the chat which will own the gift. The chat must be active in the last 24 hours.
  * @param starCount
  *   The amount of Telegram Stars that will be paid for the transfer from the business account balance. If positive,
  *   then the can_transfer_stars business bot right is required.
  */
final case class TransferGiftReq(
  businessConnectionId: String,
  ownedGiftId: String,
  newOwnerChatId: Long,
  starCount: Option[Int] = Option.empty
)
