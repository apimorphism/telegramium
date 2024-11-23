package telegramium.bots.client

import telegramium.bots.InlineQueryResult

/** @param userId
  *   Unique identifier of the target user that can use the prepared message
  * @param result
  *   A JSON-serialized object describing the message to be sent
  * @param allowUserChats
  *   Pass True if the message can be sent to private chats with users
  * @param allowBotChats
  *   Pass True if the message can be sent to private chats with bots
  * @param allowGroupChats
  *   Pass True if the message can be sent to group and supergroup chats
  * @param allowChannelChats
  *   Pass True if the message can be sent to channel chats
  */
final case class SavePreparedInlineMessageReq(
  userId: Long,
  result: InlineQueryResult,
  allowUserChats: Option[Boolean] = Option.empty,
  allowBotChats: Option[Boolean] = Option.empty,
  allowGroupChats: Option[Boolean] = Option.empty,
  allowChannelChats: Option[Boolean] = Option.empty
)
