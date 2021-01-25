package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.Emoji
import telegramium.bots.KeyboardMarkup

final case class SendDiceReq(
                             /** Unique identifier for the target chat or username of the
                               * target channel (in the format &#064;channelusername)*/
                             chatId: ChatId,
                             /** Emoji on which the dice throw animation is based.
                               * Currently, must be one of EmojiDice, EmojiDarts,
                               * EmojiBasketball, EmojiFootball, or EmojiSlotMachine. Dice
                               * can have values 1-6 for EmojiDice and EmojiDarts, values 1-5
                               * for EmojiBasketball and EmojiFootball, and values 1-64 for
                               * EmojiSlotMachine. Defaults to EmojiDice*/
                             emoji: Option[Emoji] = Option.empty,
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
