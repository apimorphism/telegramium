package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param inviteLink
  *   The invite link to edit
  * @param name
  *   Invite link name; 0-32 characters
  * @param expireDate
  *   Point in time (Unix timestamp) when the link will expire
  * @param memberLimit
  *   The maximum number of users that can be members of the chat simultaneously after joining the chat via this invite
  *   link; 1-99999
  * @param createsJoinRequest
  *   True, if users joining the chat via the link need to be approved by chat administrators. If True, member_limit
  *   can't be specified
  */
final case class EditChatInviteLinkReq(
  chatId: ChatId,
  inviteLink: String,
  name: Option[String] = Option.empty,
  expireDate: Option[Long] = Option.empty,
  memberLimit: Option[Int] = Option.empty,
  createsJoinRequest: Option[Boolean] = Option.empty
)
