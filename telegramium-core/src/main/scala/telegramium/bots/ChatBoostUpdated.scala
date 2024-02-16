package telegramium.bots

/** This object represents a boost added to a chat or changed.
  *
  * @param chat
  *   Chat which was boosted
  * @param boost
  *   Information about the chat boost
  */
final case class ChatBoostUpdated(chat: Chat, boost: ChatBoost)
