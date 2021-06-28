package telegramium.bots

/** This object represents a service message about new members invited to a voice chat.
  *
  * @param users
  *   Optional. New members that were invited to the voice chat
  */
final case class VoiceChatParticipantsInvited(users: List[User] = List.empty)
