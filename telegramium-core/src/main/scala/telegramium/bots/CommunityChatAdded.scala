package telegramium.bots

/** Describes a service message about a chat or a bot being added to a community.
  *
  * @param community
  *   The new community to which the chat or the bot belongs
  */
final case class CommunityChatAdded(community: Community)
