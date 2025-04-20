package telegramium.bots

/** Describes a service message about a change in the price of paid messages within a chat.
  *
  * @param paidMessageStarCount
  *   The new number of Telegram Stars that must be paid by non-administrator users of the supergroup chat for each sent
  *   message
  */
final case class PaidMessagePriceChanged(paidMessageStarCount: Int)
