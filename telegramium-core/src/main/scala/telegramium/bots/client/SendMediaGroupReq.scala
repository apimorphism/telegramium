package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputMedia

final case class SendMediaGroupReq(
                                   /** Unique identifier for the target chat or username of the
                                     * target channel (in the format @channelusername)*/
                                   chatId: ChatId,
                                   /** A JSON-serialized array describing photos and videos to be
                                     * sent, must include 2–10 items*/
                                   media: List[InputMedia] = List.empty,
                                   /** Sends the messages silently. Users will receive a
                                     * notification with no sound.*/
                                   disableNotification: Option[Boolean] = Option.empty,
                                   /** If the messages are a reply, ID of the original message*/
                                   replyToMessageId: Option[Int] = Option.empty)
