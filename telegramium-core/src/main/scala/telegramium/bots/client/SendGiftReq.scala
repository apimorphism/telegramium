package telegramium.bots.client

import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity

/** @param userId
  *   Unique identifier of the target user that will receive the gift
  * @param giftId
  *   Identifier of the gift
  * @param payForUpgrade
  *   Pass True to pay for the gift upgrade from the bot's balance, thereby making the upgrade free for the receiver
  * @param text
  *   Text that will be shown along with the gift; 0-255 characters
  * @param textParseMode
  *   Mode for parsing entities in the text. See formatting options for more details. Entities other than “bold”,
  *   “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji” are ignored.
  * @param textEntities
  *   A JSON-serialized list of special entities that appear in the gift text. It can be specified instead of
  *   text_parse_mode. Entities other than “bold”, “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji”
  *   are ignored.
  */
final case class SendGiftReq(
  userId: Long,
  giftId: String,
  payForUpgrade: Option[Boolean] = Option.empty,
  text: Option[String] = Option.empty,
  textParseMode: Option[ParseMode] = Option.empty,
  textEntities: List[MessageEntity] = List.empty
)
