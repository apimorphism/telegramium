package telegramium.bots

/** This object contains information about a poll.*/
final case class Poll(
                      /** Unique poll identifier*/
                      id: String,
                      /** Poll question, 1-255 characters*/
                      question: String,
                      /** List of poll options*/
                      options: List[PollOption] = List.empty,
                      /** Total number of users that voted in the poll*/
                      totalVoterCount: Int,
                      /** True, if the poll is closed*/
                      isClosed: Boolean,
                      /** True, if the poll is anonymous*/
                      isAnonymous: Boolean,
                      /** Poll type, currently can be “regular” or “quiz”*/
                      `type`: String,
                      /** True, if the poll allows multiple answers*/
                      allowsMultipleAnswers: Boolean,
                      /** Optional. 0-based identifier of the correct answer option.
                        * Available only for polls in the quiz mode, which are closed,
                        * or was sent (not forwarded) by the bot or to the private
                        * chat with the bot.*/
                      correctOptionId: Option[Int] = Option.empty,
                      /** Optional. Text that is shown when a user chooses an
                        * incorrect answer or taps on the lamp icon in a quiz-style
                        * poll, 0-200 characters*/
                      explanation: Option[String] = Option.empty,
                      /** Optional. Special entities like usernames, URLs, bot
                        * commands, etc. that appear in the explanation*/
                      explanationEntities: List[MessageEntity] = List.empty,
                      /** Optional. Amount of time in seconds the poll will be active
                        * after creation*/
                      openPeriod: Option[Int] = Option.empty,
                      /** Optional. Point in time (Unix timestamp) when the poll will
                        * be automatically closed*/
                      closeDate: Option[Int] = Option.empty)
