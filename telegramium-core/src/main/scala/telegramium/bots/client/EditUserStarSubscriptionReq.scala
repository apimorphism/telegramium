package telegramium.bots.client

/** @param userId
  *   Identifier of the user whose subscription will be edited
  * @param telegramPaymentChargeId
  *   Telegram payment identifier for the subscription
  * @param isCanceled
  *   Pass True to cancel extension of the user subscription; the subscription must be active up to the end of the
  *   current subscription period. Pass False to allow the user to re-enable a subscription that was previously canceled
  *   by the bot.
  */
final case class EditUserStarSubscriptionReq(userId: Long, telegramPaymentChargeId: String, isCanceled: Boolean)
