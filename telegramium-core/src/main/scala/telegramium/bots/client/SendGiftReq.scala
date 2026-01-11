package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity

/** @param giftId
  *   Identifier of the gift; limited gifts can't be sent to channel chats
  * @param userId
  *   Required if chat_id is not specified. Unique identifier of the target user who will receive the gift.
  * @param chatId
  *   Required if user_id is not specified. Unique identifier for the chat or username of the channel (in the format
  *   &#064;channelusername) that will receive the gift.
  * @param payForUpgrade
  *   Pass True to pay for the gift upgrade from the bot's balance, thereby making the upgrade free for the receiver
  * @param text
  *   Text that will be shown along with the gift; 0-128 characters
  * @param textParseMode
  *   Mode for parsing entities in the text. See formatting options for more details. Entities other than “bold”,
  *   “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji” are ignored.
  * @param textEntities
  *   A JSON-serialized list of special entities that appear in the gift text. It can be specified instead of
  *   text_parse_mode. Entities other than “bold”, “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji”
  *   are ignored.
  */
final case class SendGiftReq(
  giftId: String,
  userId: Option[Long] = Option.empty,
  chatId: Option[ChatId] = Option.empty,
  payForUpgrade: Option[Boolean] = Option.empty,
  text: Option[String] = Option.empty,
  textParseMode: Option[ParseMode] = Option.empty,
  textEntities: List[MessageEntity] = List.empty
)
