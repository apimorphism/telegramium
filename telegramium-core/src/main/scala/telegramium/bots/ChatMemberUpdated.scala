package telegramium.bots

/** This object represents changes in the status of a chat member.
  *
  * @param chat Chat the user belongs to
  * @param from Performer of the action, which resulted in the change
  * @param date Date the change was done in Unix time
  * @param oldChatMember Previous information about the chat member
  * @param newChatMember New information about the chat member
  * @param inviteLink Optional. Chat invite link, which was used by the user to
  * join the chat; for joining by invite link events only. */
final case class ChatMemberUpdated(chat: Chat,
                                   from: User,
                                   date: Int,
                                   oldChatMember: ChatMember,
                                   newChatMember: ChatMember,
                                   inviteLink: Option[ChatInviteLink] = Option.empty)
