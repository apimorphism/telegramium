package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param messageThreadId
  *   Unique identifier for the target message thread of the forum topic
  * @param name
  *   New topic name, 1-128 characters
  * @param iconCustomEmojiId
  *   New unique identifier of the custom emoji shown as the topic icon. Use getForumTopicIconStickers to get all
  *   allowed custom emoji identifiers
  */
final case class EditForumTopicReq(chatId: ChatId, messageThreadId: Int, name: String, iconCustomEmojiId: String)
