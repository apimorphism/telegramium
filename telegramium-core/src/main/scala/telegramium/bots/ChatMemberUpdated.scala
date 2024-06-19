package telegramium.bots

/** This object represents changes in the status of a chat member.
  *
  * @param chat
  *   Chat the user belongs to
  * @param from
  *   Performer of the action, which resulted in the change
  * @param date
  *   Date the change was done in Unix time
  * @param oldChatMember
  *   Previous information about the chat member
  * @param newChatMember
  *   New information about the chat member
  * @param inviteLink
  *   Optional. Chat invite link, which was used by the user to join the chat; for joining by invite link events only.
  * @param viaJoinRequest
  *   Optional. True, if the user joined the chat after sending a direct join request without using an invite link and
  *   being approved by an administrator
  * @param viaChatFolderInviteLink
  *   Optional. True, if the user joined the chat via a chat folder invite link
  */
final case class ChatMemberUpdated(
  chat: Chat,
  from: User,
  date: Int,
  oldChatMember: iozhik.OpenEnum[ChatMember],
  newChatMember: iozhik.OpenEnum[ChatMember],
  inviteLink: Option[ChatInviteLink] = Option.empty,
  viaJoinRequest: Option[Boolean] = Option.empty,
  viaChatFolderInviteLink: Option[Boolean] = Option.empty
)
