package telegramium.bots

/** This object contains information about changes to a user payment subscription toward the current bot.
  *
  * @param user
  *   User who subscribed for payments toward the bot
  * @param invoicePayload
  *   Bot-specified invoice payload
  * @param state
  *   The new state of the subscription. Currently, it can be one of “canceled” if the user canceled the subscription,
  *   “active” if the user re-enabled a previously canceled subscription, or “failed” if payment for the subscription
  *   failed.
  */
final case class BotSubscriptionUpdated(user: User, invoicePayload: String, state: String)
