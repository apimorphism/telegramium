package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param messageThreadId
  *   Unique identifier for the target message thread of the forum topic
  * @param name
  *   New topic name, 0-128 characters. If not specified or empty, the current name of the topic will be kept
  * @param iconCustomEmojiId
  *   New unique identifier of the custom emoji shown as the topic icon. Use getForumTopicIconStickers to get all
  *   allowed custom emoji identifiers. Pass an empty string to remove the icon. If not specified, the current icon will
  *   be kept
  */
final case class EditForumTopicReq(
  chatId: ChatId,
  messageThreadId: Int,
  name: Option[String] = Option.empty,
  iconCustomEmojiId: Option[String] = Option.empty
)
