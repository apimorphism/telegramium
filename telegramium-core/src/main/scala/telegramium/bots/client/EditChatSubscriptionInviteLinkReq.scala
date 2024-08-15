package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param inviteLink
  *   The invite link to edit
  * @param name
  *   Invite link name; 0-32 characters
  */
final case class EditChatSubscriptionInviteLinkReq(
  chatId: ChatId,
  inviteLink: String,
  name: Option[String] = Option.empty
)
