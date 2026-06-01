package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.MessageEntity
import telegramium.bots.InputPollOption
import telegramium.bots.ParseMode
import telegramium.bots.InputMedia
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot, supergroup or channel in the format
  *   &#064;username. Polls can't be sent to channel direct messages chats.
  * @param question
  *   Poll question, 1-300 characters
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param questionParseMode
  *   Mode for parsing entities in the question. See formatting options for more details. Currently, only custom emoji
  *   entities are allowed.
  * @param questionEntities
  *   A JSON-serialized list of special entities that appear in the poll question. It can be specified instead of
  *   question_parse_mode.
  * @param options
  *   A JSON-serialized list of 1-12 answer options
  * @param isAnonymous
  *   True, if the poll needs to be anonymous, defaults to True
  * @param type
  *   Poll type, “quiz” or “regular”, defaults to “regular”
  * @param allowsMultipleAnswers
  *   Pass True, if the poll allows multiple answers, defaults to False
  * @param allowsRevoting
  *   Pass True, if the poll allows to change chosen answer options, defaults to False for quizzes and to True for
  *   regular polls
  * @param shuffleOptions
  *   Pass True, if the poll options must be shown in random order
  * @param allowAddingOptions
  *   Pass True, if answer options can be added to the poll after creation; not supported for anonymous polls and
  *   quizzes
  * @param hideResultsUntilCloses
  *   Pass True, if poll results must be shown only after the poll closes
  * @param membersOnly
  *   Pass True, if voting is limited to users who have been members of the chat where the poll is being sent for more
  *   than 24 hours; for channel chats only
  * @param countryCodes
  *   A JSON-serialized list of 0-12 two-letter ISO 3166-1 alpha-2 country codes indicating the countries from which
  *   users can vote in the poll; for channel chats only. Use “FT” as a country code to allow users with anonymous
  *   numbers to vote. If omitted or empty, then users from any country can participate in the poll.
  * @param correctOptionIds
  *   A JSON-serialized list of monotonically increasing 0-based identifiers of the correct answer options, required for
  *   polls in quiz mode
  * @param explanation
  *   Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200
  *   characters with at most 2 line feeds after entities parsing
  * @param explanationParseMode
  *   Mode for parsing entities in the explanation. See formatting options for more details.
  * @param explanationEntities
  *   A JSON-serialized list of special entities that appear in the poll explanation. It can be specified instead of
  *   explanation_parse_mode.
  * @param explanationMedia
  *   Media added to the quiz explanation
  * @param openPeriod
  *   Amount of time in seconds the poll will be active after creation, 5-2628000. Can't be used together with
  *   close_date.
  * @param closeDate
  *   Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than
  *   2628000 seconds in the future. Can't be used together with open_period.
  * @param isClosed
  *   Pass True if the poll needs to be immediately closed. This can be useful for poll preview.
  * @param description
  *   Description of the poll to be sent, 0-1024 characters after entities parsing
  * @param descriptionParseMode
  *   Mode for parsing entities in the poll description. See formatting options for more details.
  * @param descriptionEntities
  *   A JSON-serialized list of special entities that appear in the poll description, which can be specified instead of
  *   description_parse_mode
  * @param media
  *   Media added to the poll description
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance.
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove a reply keyboard or to force a reply from the user.
  */
final case class SendPollReq(
  chatId: ChatId,
  question: String,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  questionParseMode: Option[String] = Option.empty,
  questionEntities: List[MessageEntity] = List.empty,
  options: List[InputPollOption] = List.empty,
  isAnonymous: Option[Boolean] = Option.empty,
  `type`: Option[String] = Option.empty,
  allowsMultipleAnswers: Option[Boolean] = Option.empty,
  allowsRevoting: Option[Boolean] = Option.empty,
  shuffleOptions: Option[Boolean] = Option.empty,
  allowAddingOptions: Option[Boolean] = Option.empty,
  hideResultsUntilCloses: Option[Boolean] = Option.empty,
  membersOnly: Option[Boolean] = Option.empty,
  countryCodes: List[String] = List.empty,
  correctOptionIds: List[Int] = List.empty,
  explanation: Option[String] = Option.empty,
  explanationParseMode: Option[ParseMode] = Option.empty,
  explanationEntities: List[MessageEntity] = List.empty,
  explanationMedia: Option[InputMedia] = Option.empty,
  openPeriod: Option[Int] = Option.empty,
  closeDate: Option[Long] = Option.empty,
  isClosed: Option[Boolean] = Option.empty,
  description: Option[String] = Option.empty,
  descriptionParseMode: Option[String] = Option.empty,
  descriptionEntities: List[MessageEntity] = List.empty,
  media: Option[InputMedia] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
