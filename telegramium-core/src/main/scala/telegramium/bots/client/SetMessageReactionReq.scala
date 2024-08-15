package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ReactionType

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param messageId
  *   Identifier of the target message. If the message belongs to a media group, the reaction is set to the first
  *   non-deleted message in the group instead.
  * @param reaction
  *   A JSON-serialized list of reaction types to set on the message. Currently, as non-premium users, bots can set up
  *   to one reaction per message. A custom emoji reaction can be used if it is either already present on the message or
  *   explicitly allowed by chat administrators. Paid reactions can't be used by bots.
  * @param isBig
  *   Pass True to set the reaction with a big animation
  */
final case class SetMessageReactionReq(
  chatId: ChatId,
  messageId: Int,
  reaction: List[ReactionType] = List.empty,
  isBig: Option[Boolean] = Option.empty
)
