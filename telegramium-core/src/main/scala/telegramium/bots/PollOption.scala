package telegramium.bots

/** This object contains information about one answer option in a poll.
  *
  * @param persistentId
  *   Unique identifier of the option, persistent on option addition and deletion
  * @param text
  *   Option text, 1-100 characters
  * @param voterCount
  *   Number of users who voted for this option; may be 0 if unknown
  * @param textEntities
  *   Optional. Special entities that appear in the option text. Currently, only custom emoji entities are allowed in
  *   poll option texts
  * @param media
  *   Optional. Media added to the poll option
  * @param addedByUser
  *   Optional. User who added the option; omitted if the option wasn't added by a user after poll creation
  * @param addedByChat
  *   Optional. Chat that added the option; omitted if the option wasn't added by a chat after poll creation
  * @param additionDate
  *   Optional. Point in time (Unix timestamp) when the option was added; omitted if the option existed in the original
  *   poll
  */
final case class PollOption(
  persistentId: String,
  text: String,
  voterCount: Int,
  textEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  media: Option[PollMedia] = Option.empty,
  addedByUser: Option[User] = Option.empty,
  addedByChat: Option[Chat] = Option.empty,
  additionDate: Option[Long] = Option.empty
)
