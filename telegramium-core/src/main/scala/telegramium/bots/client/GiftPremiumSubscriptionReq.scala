package telegramium.bots.client

import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity

/** @param userId
  *   Unique identifier of the target user who will receive a Telegram Premium subscription
  * @param monthCount
  *   Number of months the Telegram Premium subscription will be active for the user; must be one of 3, 6, or 12
  * @param starCount
  *   Number of Telegram Stars to pay for the Telegram Premium subscription; must be 1000 for 3 months, 1500 for 6
  *   months, and 2500 for 12 months
  * @param text
  *   Text that will be shown along with the service message about the subscription; 0-128 characters
  * @param textParseMode
  *   Mode for parsing entities in the text. See formatting options for more details. Entities other than “bold”,
  *   “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji” are ignored.
  * @param textEntities
  *   A JSON-serialized list of special entities that appear in the gift text. It can be specified instead of
  *   text_parse_mode. Entities other than “bold”, “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji”
  *   are ignored.
  */
final case class GiftPremiumSubscriptionReq(
  userId: Long,
  monthCount: Int,
  starCount: Int,
  text: Option[String] = Option.empty,
  textParseMode: Option[ParseMode] = Option.empty,
  textEntities: List[MessageEntity] = List.empty
)
