package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param starCount
  *   Number of Telegram Stars to transfer; 1-10000
  */
final case class TransferBusinessAccountStarsReq(businessConnectionId: String, starCount: Int)
