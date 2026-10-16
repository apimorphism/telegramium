package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier of the target chat or username of the target channel in the format <code>&#064;username</code>
  * @param inviteLink
  *   The invite link to revoke
  */
final case class RevokeChatInviteLinkReq(chatId: ChatId, inviteLink: String)
