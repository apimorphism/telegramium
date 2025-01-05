package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param customDescription
  *   Custom description for the verification; 0-70 characters. Must be empty if the organization isn't allowed to
  *   provide a custom verification description.
  */
final case class VerifyChatReq(chatId: ChatId, customDescription: Option[String] = Option.empty)
