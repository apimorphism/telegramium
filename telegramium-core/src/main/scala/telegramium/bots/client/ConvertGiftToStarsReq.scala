package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param ownedGiftId
  *   Unique identifier of the regular gift that should be converted to Telegram Stars
  */
final case class ConvertGiftToStarsReq(businessConnectionId: String, ownedGiftId: String)
