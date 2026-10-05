package telegramium.bots

/** Describes a service message about a chat being joined by a user from a community.
  *
  * @param community
  *   The community from which the chat was joined
  */
final case class CommunityChatJoined(community: Community)
