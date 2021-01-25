package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

final case class SendPollReq(
                             /** Unique identifier for the target chat or username of the
                               * target channel (in the format &#064;channelusername)*/
                             chatId: ChatId,
                             /** Poll question, 1-300 characters*/
                             question: String,
                             /** A JSON-serialized list of answer options, 2-10 strings
                               * 1-100 characters each*/
                             options: List[String] = List.empty,
                             /** True, if the poll needs to be anonymous, defaults to True*/
                             isAnonymous: Option[Boolean] = Option.empty,
                             /** Poll type, “quiz” or “regular”, defaults to “regular”*/
                             `type`: Option[String] = Option.empty,
                             /** True, if the poll allows multiple answers, ignored for
                               * polls in quiz mode, defaults to False*/
                             allowsMultipleAnswers: Option[Boolean] = Option.empty,
                             /** 0-based identifier of the correct answer option, required
                               * for polls in quiz mode*/
                             correctOptionId: Option[Int] = Option.empty,
                             /** Text that is shown when a user chooses an incorrect answer
                               * or taps on the lamp icon in a quiz-style poll, 0-200
                               * characters with at most 2 line feeds after entities parsing*/
                             explanation: Option[String] = Option.empty,
                             /** Mode for parsing entities in the explanation. See
                               * formatting options for more details.*/
                             explanationParseMode: Option[String] = Option.empty,
                             /** List of special entities that appear in the poll
                               * explanation, which can be specified instead of parse_mode*/
                             explanationEntities: List[MessageEntity] = List.empty,
                             /** Amount of time in seconds the poll will be active after
                               * creation, 5-600. Can't be used together with close_date.*/
                             openPeriod: Option[Int] = Option.empty,
                             /** Point in time (Unix timestamp) when the poll will be
                               * automatically closed. Must be at least 5 and no more than
                               * 600 seconds in the future. Can't be used together with
                               * open_period.*/
                             closeDate: Option[Int] = Option.empty,
                             /** Pass True, if the poll needs to be immediately closed. This
                               * can be useful for poll preview.*/
                             isClosed: Option[Boolean] = Option.empty,
                             /** Sends the message silently. Users will receive a
                               * notification with no sound.*/
                             disableNotification: Option[Boolean] = Option.empty,
                             /** If the message is a reply, ID of the original message*/
                             replyToMessageId: Option[Int] = Option.empty,
                             /** Pass True, if the message should be sent even if the
                               * specified replied-to message is not found*/
                             allowSendingWithoutReply: Option[Boolean] = Option.empty,
                             /** Additional interface options. A JSON-serialized object for
                               * an inline keyboard, custom reply keyboard, instructions to
                               * remove reply keyboard or to force a reply from the user.*/
                             replyMarkup: Option[KeyboardMarkup] = Option.empty)
