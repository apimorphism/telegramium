package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.KeyboardMarkup

final case class SendPollReq(
                             /** Unique identifier for the target chat or username of the
                               * target channel (in the format @channelusername)*/
                             chatId: ChatId,
                             /** Poll question, 1-255 characters*/
                             question: String,
                             /** List of answer options, 2-10 strings 1-100 characters each*/
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
                             /** Pass True, if the poll needs to be immediately closed*/
                             isClosed: Option[Boolean] = Option.empty,
                             /** Sends the message silently. Users will receive a
                               * notification with no sound.*/
                             disableNotification: Option[Boolean] = Option.empty,
                             /** If the message is a reply, ID of the original message*/
                             replyToMessageId: Option[Int] = Option.empty,
                             /** Additional interface options. A JSON-serialized object for
                               * an inline keyboard, custom reply keyboard, instructions to
                               * remove reply keyboard or to force a reply from the user.*/
                             replyMarkup: Option[KeyboardMarkup] = Option.empty)
