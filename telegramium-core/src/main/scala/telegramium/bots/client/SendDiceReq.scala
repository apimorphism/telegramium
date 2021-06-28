package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.Emoji
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param emoji
  *   Emoji on which the dice throw animation is based. Currently, must be one of EmojiDice, EmojiDarts,
  *   EmojiBasketball, EmojiFootball, EmojiBowling or EmojiSlotMachine. Dice can have values 1-6 for EmojiDice,
  *   EmojiDarts and EmojiBowling, values 1-5 for EmojiBasketball and EmojiFootball, and values 1-64 for
  *   EmojiSlotMachine. Defaults to EmojiDice
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param replyToMessageId
  *   If the message is a reply, ID of the original message
  * @param allowSendingWithoutReply
  *   Pass True, if the message should be sent even if the specified replied-to message is not found
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove reply keyboard or to force a reply from the user.
  */
final case class SendDiceReq(
  chatId: ChatId,
  emoji: Option[Emoji] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
