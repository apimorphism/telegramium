package telegramium.bots

/** Represents an invite link for a chat.
  *
  * @param inviteLink
  *   The invite link. If the link was created by another chat administrator, then the second part of the link will be
  *   replaced with “…”.
  * @param creator
  *   Creator of the link
  * @param createsJoinRequest
  *   True, if users joining the chat via the link need to be approved by chat administrators
  * @param isPrimary
  *   True, if the link is primary
  * @param isRevoked
  *   True, if the link is revoked
  * @param name
  *   Optional. Invite link name
  * @param expireDate
  *   Optional. Point in time (Unix timestamp) when the link will expire or has been expired
  * @param memberLimit
  *   Optional. The maximum number of users that can be members of the chat simultaneously after joining the chat via
  *   this invite link; 1-99999
  * @param pendingJoinRequestCount
  *   Optional. Number of pending join requests created using this link
  * @param subscriptionPeriod
  *   Optional. The number of seconds the subscription will be active for before the next payment
  * @param subscriptionPrice
  *   Optional. The amount of Telegram Stars a user must pay initially and after each subsequent subscription period to
  *   be a member of the chat using the link
  */
final case class ChatInviteLink(
  inviteLink: String,
  creator: User,
  createsJoinRequest: Boolean,
  isPrimary: Boolean,
  isRevoked: Boolean,
  name: Option[String] = Option.empty,
  expireDate: Option[Int] = Option.empty,
  memberLimit: Option[Int] = Option.empty,
  pendingJoinRequestCount: Option[Int] = Option.empty,
  subscriptionPeriod: Option[Int] = Option.empty,
  subscriptionPrice: Option[Int] = Option.empty
)
