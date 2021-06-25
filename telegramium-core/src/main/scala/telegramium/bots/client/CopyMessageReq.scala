package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

/**
 * @param chatId Unique identifier for the target chat or username of the
 * target channel (in the format &#064;channelusername)
 * @param fromChatId Unique identifier for the chat where the original message
 * was sent (or channel username in the format
 * &#064;channelusername)
 * @param messageId Message identifier in the chat specified in from_chat_id
 * @param caption New caption for media, 0-1024 characters after entities
 * parsing. If not specified, the original caption is kept
 * @param parseMode Mode for parsing entities in the new caption. See
 * formatting options for more details.
 * @param captionEntities List of special entities that appear in the new caption,
 * which can be specified instead of parse_mode
 * @param disableNotification Sends the message silently. Users will receive a
 * notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
 * specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for
 * an inline keyboard, custom reply keyboard, instructions to
 * remove reply keyboard or to force a reply from the user.
 */
final case class CopyMessageReq(chatId: ChatId,
                                fromChatId: ChatId,
                                messageId: Int,
                                caption: Option[String] = Option.empty,
                                parseMode: Option[ParseMode] = Option.empty,
                                captionEntities: List[MessageEntity] = List.empty,
                                disableNotification: Option[Boolean] = Option.empty,
                                replyToMessageId: Option[Int] = Option.empty,
                                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                                replyMarkup: Option[KeyboardMarkup] = Option.empty
)
