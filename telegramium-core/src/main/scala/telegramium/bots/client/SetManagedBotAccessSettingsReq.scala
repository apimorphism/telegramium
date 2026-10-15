package telegramium.bots.client

/** @param userId
  *   User identifier of the managed bot whose access settings will be changed
  * @param isAccessRestricted
  *   Pass <em>True</em> if only selected users can access the bot. The bot's owner can always access it.
  * @param addedUserIds
  *   A JSON-serialized list of up to 10 identifiers of users who will have access to the bot in addition to its owner.
  *   Ignored if <em>is_access_restricted</em> is <em>False</em>.
  */
final case class SetManagedBotAccessSettingsReq(
  userId: Long,
  isAccessRestricted: Boolean,
  addedUserIds: List[Long] = List.empty
)
