package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputMedia

final case class SendMediaGroupReq(
                                   /** Unique identifier for the target chat or username of the
                                     * target channel (in the format &#064;channelusername)*/
                                   chatId: ChatId,
                                   /** A JSON-serialized array describing messages to be sent,
                                     * must include 2-10 items*/
                                   media: List[InputMedia] = List.empty,
                                   /** Sends messages silently. Users will receive a notification
                                     * with no sound.*/
                                   disableNotification: Option[Boolean] = Option.empty,
                                   /** If the messages are a reply, ID of the original message*/
                                   replyToMessageId: Option[Int] = Option.empty,
                                   /** Pass True, if the message should be sent even if the
                                     * specified replied-to message is not found*/
                                   allowSendingWithoutReply: Option[Boolean] = Option.empty)
