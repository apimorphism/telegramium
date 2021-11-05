package telegramium.bots

/** Represents a join request sent to a chat.
  *
  * @param chat
  *   Chat to which the request was sent
  * @param from
  *   User that sent the join request
  * @param date
  *   Date the request was sent in Unix time
  * @param bio
  *   Optional. Bio of the user.
  * @param inviteLink
  *   Optional. Chat invite link that was used by the user to send the join request
  */
final case class ChatJoinRequest(
  chat: Chat,
  from: User,
  date: Int,
  bio: Option[String] = Option.empty,
  inviteLink: Option[ChatInviteLink] = Option.empty
)
