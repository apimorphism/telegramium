package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param ownedGiftId
  *   Unique identifier of the regular gift that should be deleted
  */
final case class DeleteBusinessAccountGiftReq(businessConnectionId: String, ownedGiftId: String)
