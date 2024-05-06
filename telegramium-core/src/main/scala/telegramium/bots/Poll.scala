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
  * @param questionEntities
  *   Optional. Special entities that appear in the question. Currently, only custom emoji entities are allowed in poll
  *   questions
  * @param options
  *   List of poll options
  * @param correctOptionId
  *   Optional. 0-based identifier of the correct answer option. Available only for polls in the quiz mode, which are
  *   closed, or was sent (not forwarded) by the bot or to the private chat with the bot.
  * @param explanation
  *   Optional. Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style
  *   poll, 0-200 characters
  * @param explanationEntities
  *   Optional. Special entities like usernames, URLs, bot commands, etc. that appear in the explanation
  * @param openPeriod
  *   Optional. Amount of time in seconds the poll will be active after creation
  * @param closeDate
  *   Optional. Point in time (Unix timestamp) when the poll will be automatically closed
  */
final case class Poll(
  id: String,
  question: String,
  totalVoterCount: Int,
  isClosed: Boolean,
  isAnonymous: Boolean,
  `type`: String,
  allowsMultipleAnswers: Boolean,
  questionEntities: List[MessageEntity] = List.empty,
  options: List[PollOption] = List.empty,
  correctOptionId: Option[Int] = Option.empty,
  explanation: Option[String] = Option.empty,
  explanationEntities: List[MessageEntity] = List.empty,
  openPeriod: Option[Int] = Option.empty,
  closeDate: Option[Int] = Option.empty
)
