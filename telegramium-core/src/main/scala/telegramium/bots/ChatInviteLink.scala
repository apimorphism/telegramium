package telegramium.bots

/** Represents an invite link for a chat.
  *
  * @param inviteLink
  *   The invite link. If the link was created by another chat administrator, then the second part of the link will be
  *   replaced with “…”.
  * @param creator
  *   Creator of the link
  * @param isPrimary
  *   True, if the link is primary
  * @param isRevoked
  *   True, if the link is revoked
  * @param expireDate
  *   Optional. Point in time (Unix timestamp) when the link will expire or has been expired
  * @param memberLimit
  *   Optional. Maximum number of users that can be members of the chat simultaneously after joining the chat via this
  *   invite link; 1-99999
  */
final case class ChatInviteLink(
  inviteLink: String,
  creator: User,
  isPrimary: Boolean,
  isRevoked: Boolean,
  expireDate: Option[Int] = Option.empty,
  memberLimit: Option[Int] = Option.empty
)
