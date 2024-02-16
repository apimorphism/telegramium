package telegramium.bots

/** This object represents a service message about a user boosting a chat.
  *
  * @param boostCount
  *   Number of boosts added by the user
  */
final case class ChatBoostAdded(boostCount: Int)
