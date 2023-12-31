package telegramium.bots

/** This object represents a list of boosts added to a chat by a user.
  *
  * @param boosts
  *   The list of boosts added to the chat by the user
  */
final case class UserChatBoosts(boosts: List[ChatBoost] = List.empty)
