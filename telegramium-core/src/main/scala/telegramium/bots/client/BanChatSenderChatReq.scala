package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param senderChatId
  *   Unique identifier of the target sender chat
  * @param untilDate
  *   Date when the sender chat will be unbanned, unix time. If the chat is banned for more than 366 days or less than
  *   30 seconds from the current time they are considered to be banned forever.
  */
final case class BanChatSenderChatReq(chatId: ChatId, senderChatId: Int, untilDate: Option[Int] = Option.empty)
