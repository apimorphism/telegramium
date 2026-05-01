package telegramium.bots

/** This object contains information about a poll.
  *
  * @param id
  *   Unique poll identifier
  * @param question
  *   Poll question, 1-300 characters
  * @param totalVoterCount
  *   Total number of users that voted in the poll
  * @param isClosed
  *   True, if the poll is closed
  * @param isAnonymous
  *   True, if the poll is anonymous
  * @param type
  *   Poll type, currently can be “regular” or “quiz”
  * @param allowsMultipleAnswers
  *   True, if the poll allows multiple answers
  * @param allowsRevoting
  *   True, if the poll allows to change the chosen answer options
  * @param questionEntities
  *   Optional. Special entities that appear in the question. Currently, only custom emoji entities are allowed in poll
  *   questions
  * @param options
  *   List of poll options
  * @param correctOptionIds
  *   Optional. Array of 0-based identifiers of the correct answer options. Available only for polls in quiz mode which
  *   are closed or were sent (not forwarded) by the bot or to the private chat with the bot.
  * @param explanation
  *   Optional. Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style
  *   poll, 0-200 characters
  * @param explanationEntities
  *   Optional. Special entities like usernames, URLs, bot commands, etc. that appear in the explanation
  * @param openPeriod
  *   Optional. Amount of time in seconds the poll will be active after creation
  * @param closeDate
  *   Optional. Point in time (Unix timestamp) when the poll will be automatically closed
  * @param description
  *   Optional. Description of the poll; for polls inside the Message object only
  * @param descriptionEntities
  *   Optional. Special entities like usernames, URLs, bot commands, etc. that appear in the description
  */
final case class Poll(
  id: String,
  question: String,
  totalVoterCount: Int,
  isClosed: Boolean,
  isAnonymous: Boolean,
  `type`: String,
  allowsMultipleAnswers: Boolean,
  allowsRevoting: Boolean,
  questionEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  options: List[PollOption] = List.empty,
  correctOptionIds: List[Int] = List.empty,
  explanation: Option[String] = Option.empty,
  explanationEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  openPeriod: Option[Int] = Option.empty,
  closeDate: Option[Long] = Option.empty,
  description: Option[String] = Option.empty,
  descriptionEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty
)
