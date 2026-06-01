package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup or channel in the format &#064;username
  * @param returnBots
  *   Pass True to additionally receive all bots that are administrators of the chat. By default, bots other than the
  *   current bot are omitted.
  */
final case class GetChatAdministratorsReq(chatId: ChatId, returnBots: Option[Boolean] = Option.empty)
