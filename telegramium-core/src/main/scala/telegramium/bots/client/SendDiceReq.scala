package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.KeyboardMarkup

final case class SendDiceReq(
                             /** Unique identifier for the target chat or username of the
                               * target channel (in the format @channelusername)*/
                             chatId: ChatId,
                             /** Sends the message silently. Users will receive a
                               * notification with no sound.*/
                             disableNotification: Option[Boolean] = Option.empty,
                             /** If the message is a reply, ID of the original message*/
                             replyToMessageId: Option[Int] = Option.empty,
                             /** Additional interface options. A JSON-serialized object for
                               * an inline keyboard, custom reply keyboard, instructions to
                               * remove reply keyboard or to force a reply from the user.*/
                             replyMarkup: Option[KeyboardMarkup] = Option.empty)
