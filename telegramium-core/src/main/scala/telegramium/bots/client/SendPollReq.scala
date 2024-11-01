package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.MessageEntity
import telegramium.bots.InputPollOption
import telegramium.bots.ParseMode
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param question
  *   Poll question, 1-300 characters
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param questionParseMode
  *   Mode for parsing entities in the question. See formatting options for more details. Currently, only custom emoji
  *   entities are allowed
  * @param questionEntities
  *   A JSON-serialized list of special entities that appear in the poll question. It can be specified instead of
  *   question_parse_mode
  * @param options
  *   A JSON-serialized list of 2-10 answer options
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
  *   A JSON-serialized list of special entities that appear in the poll explanation. It can be specified instead of
  *   explanation_parse_mode
  * @param openPeriod
  *   Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
  * @param closeDate
  *   Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600
  *   seconds in the future. Can't be used together with open_period.
  * @param isClosed
  *   Pass True if the poll needs to be immediately closed. This can be useful for poll preview.
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove a reply keyboard or to force a reply from the user
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
  correctOptionId: Option[Int] = Option.empty,
  explanation: Option[String] = Option.empty,
  explanationParseMode: Option[ParseMode] = Option.empty,
  explanationEntities: List[MessageEntity] = List.empty,
  openPeriod: Option[Int] = Option.empty,
  closeDate: Option[Int] = Option.empty,
  isClosed: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
