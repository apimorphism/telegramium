package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.LabeledPrice
import telegramium.bots.SuggestedPostParameters
import telegramium.bots.ReplyParameters
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param title
  *   Product name, 1-32 characters
  * @param description
  *   Product description, 1-255 characters
  * @param payload
  *   Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use it for your internal
  *   processes.
  * @param currency
  *   Three-letter ISO 4217 currency code, see more on currencies. Pass “XTR” for payments in Telegram Stars.
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param directMessagesTopicId
  *   Identifier of the direct messages topic to which the message will be sent; required if the message is sent to a
  *   direct messages chat
  * @param providerToken
  *   Payment provider token, obtained via &#064;BotFather. Pass an empty string for payments in Telegram Stars.
  * @param prices
  *   Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery
  *   tax, bonus, etc.). Must contain exactly one item for payments in Telegram Stars.
  * @param maxTipAmount
  *   The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double). For
  *   example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145. See the exp parameter in currencies.json, it
  *   shows the number of digits past the decimal point for each currency (2 for the majority of currencies). Defaults
  *   to 0. Not supported for payments in Telegram Stars.
  * @param suggestedTipAmounts
  *   A JSON-serialized array of suggested amounts of tips in the smallest units of the currency (integer, not
  *   float/double). At most 4 suggested tip amounts can be specified. The suggested tip amounts must be positive,
  *   passed in a strictly increased order and must not exceed max_tip_amount.
  * @param startParameter
  *   Unique deep-linking parameter. If left empty, forwarded copies of the sent message will have a Pay button,
  *   allowing multiple users to pay directly from the forwarded message, using the same invoice. If non-empty,
  *   forwarded copies of the sent message will have a URL button with a deep link to the bot (instead of a Pay button),
  *   with the value used as the start parameter
  * @param providerData
  *   JSON-serialized data about the invoice, which will be shared with the payment provider. A detailed description of
  *   required fields should be provided by the payment provider.
  * @param photoUrl
  *   URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service. People
  *   like it better when they see what they are paying for.
  * @param photoSize
  *   Photo size in bytes
  * @param photoWidth
  *   Photo width
  * @param photoHeight
  *   Photo height
  * @param needName
  *   Pass True if you require the user's full name to complete the order. Ignored for payments in Telegram Stars.
  * @param needPhoneNumber
  *   Pass True if you require the user's phone number to complete the order. Ignored for payments in Telegram Stars.
  * @param needEmail
  *   Pass True if you require the user's email address to complete the order. Ignored for payments in Telegram Stars.
  * @param needShippingAddress
  *   Pass True if you require the user's shipping address to complete the order. Ignored for payments in Telegram
  *   Stars.
  * @param sendPhoneNumberToProvider
  *   Pass True if the user's phone number should be sent to the provider. Ignored for payments in Telegram Stars.
  * @param sendEmailToProvider
  *   Pass True if the user's email address should be sent to the provider. Ignored for payments in Telegram Stars.
  * @param isFlexible
  *   Pass True if the final price depends on the shipping method. Ignored for payments in Telegram Stars.
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param suggestedPostParameters
  *   A JSON-serialized object containing the parameters of the suggested post to send; for direct messages chats only.
  *   If the message is sent as a reply to another suggested post, then that suggested post is automatically declined.
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard. If empty, one 'Pay total price' button will be shown. If not
  *   empty, the first button must be a Pay button.
  */
final case class SendInvoiceReq(
  chatId: ChatId,
  title: String,
  description: String,
  payload: String,
  currency: String,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopicId: Option[Long] = Option.empty,
  providerToken: Option[String] = Option.empty,
  prices: List[LabeledPrice] = List.empty,
  maxTipAmount: Option[Long] = Option.empty,
  suggestedTipAmounts: List[Long] = List.empty,
  startParameter: Option[String] = Option.empty,
  providerData: Option[String] = Option.empty,
  photoUrl: Option[String] = Option.empty,
  photoSize: Option[Long] = Option.empty,
  photoWidth: Option[Int] = Option.empty,
  photoHeight: Option[Int] = Option.empty,
  needName: Option[Boolean] = Option.empty,
  needPhoneNumber: Option[Boolean] = Option.empty,
  needEmail: Option[Boolean] = Option.empty,
  needShippingAddress: Option[Boolean] = Option.empty,
  sendPhoneNumberToProvider: Option[Boolean] = Option.empty,
  sendEmailToProvider: Option[Boolean] = Option.empty,
  isFlexible: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  suggestedPostParameters: Option[SuggestedPostParameters] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
