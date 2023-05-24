package telegramium.bots.client

/** @param chatId
  *   Unique identifier for the target private chat. If not specified, default bot's menu button will be returned
  */
final case class GetChatMenuButtonReq(chatId: Option[Long] = Option.empty)
