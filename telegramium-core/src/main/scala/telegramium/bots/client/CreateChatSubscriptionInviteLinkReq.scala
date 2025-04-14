package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target channel chat or username of the target channel (in the format
  *   &#064;channelusername)
  * @param subscriptionPeriod
  *   The number of seconds the subscription will be active for before the next payment. Currently, it must always be
  *   2592000 (30 days).
  * @param subscriptionPrice
  *   The amount of Telegram Stars a user must pay initially and after each subsequent subscription period to be a
  *   member of the chat; 1-10000
  * @param name
  *   Invite link name; 0-32 characters
  */
final case class CreateChatSubscriptionInviteLinkReq(
  chatId: ChatId,
  subscriptionPeriod: Int,
  subscriptionPrice: Int,
  name: Option[String] = Option.empty
)
