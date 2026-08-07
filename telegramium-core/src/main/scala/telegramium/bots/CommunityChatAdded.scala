package telegramium.bots

/** Describes a service message about a chat being added to a community.
  *
  * @param community
  *   The new community to which the chat belongs
  */
final case class CommunityChatAdded(community: Community)
