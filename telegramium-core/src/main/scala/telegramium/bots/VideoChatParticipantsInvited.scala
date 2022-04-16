package telegramium.bots

/** This object represents a service message about new members invited to a video chat.
  *
  * @param users
  *   New members that were invited to the video chat
  */
final case class VideoChatParticipantsInvited(users: List[User] = List.empty)
