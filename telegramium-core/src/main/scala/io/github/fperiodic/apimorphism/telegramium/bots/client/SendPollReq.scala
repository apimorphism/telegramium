package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId
import io.github.fperiodic.apimorphism.telegramium.bots.KeyboardMarkup

final case class SendPollReq(
                             /** Unique identifier for the target chat or username of the
                               * target channel (in the format @channelusername). A native
                               * poll can't be sent to a private chat.*/
                             chatId: ChatId,
                             /** Poll question, 1-255 characters*/
                             question: String,
                             /** List of answer options, 2-10 strings 1-100 characters each*/
                             options: List[String] = List.empty,
                             /** Sends the message silently. Users will receive a
                               * notification with no sound.*/
                             disableNotification: Option[Boolean] = Option.empty,
                             /** If the message is a reply, ID of the original message*/
                             replyToMessageId: Option[Int] = Option.empty,
                             /** Additional interface options. A JSON-serialized object for
                               * an inline keyboard, custom reply keyboard, instructions to
                               * remove reply keyboard or to force a reply from the user.*/
                             replyMarkup: Option[KeyboardMarkup] = Option.empty)
