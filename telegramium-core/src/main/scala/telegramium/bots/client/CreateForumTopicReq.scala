package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param name
  *   Topic name, 1-128 characters
  * @param iconColor
  *   Color of the topic icon in RGB format. Currently, must be one of 7322096 (0x6FB9F0), 16766590 (0xFFD67E), 13338331
  *   (0xCB86DB), 9367192 (0x8EEE98), 16749490 (0xFF93B2), or 16478047 (0xFB6F5F)
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
