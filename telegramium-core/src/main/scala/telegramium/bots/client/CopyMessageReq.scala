package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

final case class CopyMessageReq(
                                /** Unique identifier for the target chat or username of the
                                  * target channel (in the format @channelusername)*/
                                chatId: ChatId,
                                /** Unique identifier for the chat where the original message
                                  * was sent (or channel username in the format
                                  * @channelusername)*/
                                fromChatId: ChatId,
                                /** Message identifier in the chat specified in from_chat_id*/
                                messageId: Int,
                                /** New caption for media, 0-1024 characters after entities
                                  * parsing. If not specified, the original caption is kept*/
                                caption: Option[String] = Option.empty,
                                /** Mode for parsing entities in the new caption. See
                                  * formatting options for more details.*/
                                parseMode: Option[ParseMode] = Option.empty,
                                /** List of special entities that appear in the new caption,
                                  * which can be specified instead of parse_mode*/
                                captionEntities: List[MessageEntity] = List.empty,
                                /** Sends the message silently. Users will receive a
                                  * notification with no sound.*/
                                disableNotification: Option[Boolean] = Option.empty,
                                /** If the message is a reply, ID of the original message*/
                                replyToMessageId: Option[Int] = Option.empty,
                                /** Pass True, if the message should be sent even if the
                                  * specified replied-to message is not found*/
                                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                                /** Additional interface options. A JSON-serialized object for
                                  * an inline keyboard, custom reply keyboard, instructions to
                                  * remove reply keyboard or to force a reply from the user.*/
                                replyMarkup: Option[KeyboardMarkup] = Option.empty)
