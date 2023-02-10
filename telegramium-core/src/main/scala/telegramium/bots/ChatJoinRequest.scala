package telegramium.bots

/** Represents a join request sent to a chat.
  *
  * @param chat
  *   Chat to which the request was sent
  * @param from
  *   User that sent the join request
  * @param userChatId
  *   Identifier of a private chat with the user who sent the join request. This number may have more than 32
  *   significant bits and some programming languages may have difficulty/silent defects in interpreting it. But it has
  *   at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this
  *   identifier. The bot can use this identifier for 24 hours to send messages until the join request is processed,
  *   assuming no other administrator contacted the user.
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
  userChatId: Long,
  date: Int,
  bio: Option[String] = Option.empty,
  inviteLink: Option[ChatInviteLink] = Option.empty
)
