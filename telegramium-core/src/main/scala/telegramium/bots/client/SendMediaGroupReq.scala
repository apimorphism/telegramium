package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputMedia

/** @param chatId Unique identifier for the target chat or username of the
  * target channel (in the format &#064;channelusername)
  * @param media A JSON-serialized array describing messages to be sent,
  * must include 2-10 items
  * @param disableNotification Sends messages silently. Users will receive a notification
  * with no sound.
  * @param replyToMessageId If the messages are a reply, ID of the original message
  * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
  * specified replied-to message is not found */
final case class SendMediaGroupReq(chatId: ChatId,
                                   media: List[InputMedia] = List.empty,
                                   disableNotification: Option[Boolean] = Option.empty,
                                   replyToMessageId: Option[Int] = Option.empty,
                                   allowSendingWithoutReply: Option[Boolean] = Option.empty)
