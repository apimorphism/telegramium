package telegramium.bots

/** Describes a service message about a change in the price of direct messages sent to a channel chat.
  *
  * @param areDirectMessagesEnabled
  *   True, if direct messages are enabled for the channel chat; false otherwise
  * @param directMessageStarCount
  *   Optional. The new number of Telegram Stars that must be paid by users for each direct message sent to the channel.
  *   Does not apply to users who have been exempted by administrators. Defaults to 0.
  */
final case class DirectMessagePriceChanged(
  areDirectMessagesEnabled: Boolean,
  directMessageStarCount: Option[Int] = Option.empty
)
