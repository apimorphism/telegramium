package telegramium.bots.client

/** @param userId
  *   Identifier of the user whose payment will be refunded
  * @param telegramPaymentChargeId
  *   Telegram payment identifier
  */
final case class RefundStarPaymentReq(userId: Long, telegramPaymentChargeId: String)
