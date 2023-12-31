package telegramium.bots

/** This object contains information about a chat boost.
  *
  * @param boostId
  *   Unique identifier of the boost
  * @param addDate
  *   Point in time (Unix timestamp) when the chat was boosted
  * @param expirationDate
  *   Point in time (Unix timestamp) when the boost will automatically expire, unless the booster's Telegram Premium
  *   subscription is prolonged
  * @param source
  *   Source of the added boost
  */
final case class ChatBoost(boostId: String, addDate: Int, expirationDate: Int, source: ChatBoostSource)
