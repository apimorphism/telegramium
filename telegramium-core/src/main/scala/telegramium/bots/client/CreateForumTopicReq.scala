package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param name
  *   Topic name, 1-128 characters
  * @param iconColor
  *   Color of the topic icon in RGB format. Currently, must be one of 0x6FB9F0, 0xFFD67E, 0xCB86DB, 0x8EEE98, 0xFF93B2,
  *   or 0xFB6F5F
  * @param iconCustomEmojiId
  *   Unique identifier of the custom emoji shown as the topic icon. Use getForumTopicIconStickers to get all allowed
  *   custom emoji identifiers.
  */
final case class CreateForumTopicReq(
  chatId: ChatId,
  name: String,
  iconColor: Option[Int] = Option.empty,
  iconCustomEmojiId: Option[String] = Option.empty
)
