package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param question
  *   Poll question, 1-300 characters
  * @param options
  *   A JSON-serialized list of answer options, 2-10 strings 1-100 characters each
  * @param isAnonymous
  *   True, if the poll needs to be anonymous, defaults to True
  * @param type
  *   Poll type, “quiz” or “regular”, defaults to “regular”
  * @param allowsMultipleAnswers
  *   True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
  * @param correctOptionId
  *   0-based identifier of the correct answer option, required for polls in quiz mode
  * @param explanation
  *   Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200
  *   characters with at most 2 line feeds after entities parsing
  * @param explanationParseMode
  *   Mode for parsing entities in the explanation. See formatting options for more details.
  * @param explanationEntities
  *   A JSON-serialized list of special entities that appear in the poll explanation, which can be specified instead of
  *   parse_mode
  * @param openPeriod
  *   Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
  * @param closeDate
  *   Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600
  *   seconds in the future. Can't be used together with open_period.
  * @param isClosed
  *   Pass True, if the poll needs to be immediately closed. This can be useful for poll preview.
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param replyToMessageId
  *   If the message is a reply, ID of the original message
  * @param allowSendingWithoutReply
  *   Pass True, if the message should be sent even if the specified replied-to message is not found
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove reply keyboard or to force a reply from the user.
  */
final case class SendPollReq(
  chatId: ChatId,
  question: String,
  options: List[String] = List.empty,
  isAnonymous: Option[Boolean] = Option.empty,
  `type`: Option[String] = Option.empty,
  allowsMultipleAnswers: Option[Boolean] = Option.empty,
  correctOptionId: Option[Int] = Option.empty,
  explanation: Option[String] = Option.empty,
  explanationParseMode: Option[String] = Option.empty,
  explanationEntities: List[MessageEntity] = List.empty,
  openPeriod: Option[Int] = Option.empty,
  closeDate: Option[Int] = Option.empty,
  isClosed: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
