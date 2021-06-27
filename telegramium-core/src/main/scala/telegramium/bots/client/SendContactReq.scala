package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param phoneNumber
  *   Contact's phone number
  * @param firstName
  *   Contact's first name
  * @param lastName
  *   Contact's last name
  * @param vcard
  *   Additional data about the contact in the form of a vCard, 0-2048 bytes
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param replyToMessageId
  *   If the message is a reply, ID of the original message
  * @param allowSendingWithoutReply
  *   Pass True, if the message should be sent even if the specified replied-to message is not found
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove keyboard or to force a reply from the user.
  */
final case class SendContactReq(
  chatId: ChatId,
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = Option.empty,
  vcard: Option[String] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
