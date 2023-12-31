package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.LabeledPrice
import telegramium.bots.ReplyParameters
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param title
  *   Product name, 1-32 characters
  * @param description
  *   Product description, 1-255 characters
  * @param payload
  *   Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal processes.
  * @param providerToken
  *   Payment provider token, obtained via &#064;BotFather
  * @param currency
  *   Three-letter ISO 4217 currency code, see more on currencies
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param prices
  *   Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery
  *   tax, bonus, etc.)
  * @param maxTipAmount
  *   The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double). For
  *   example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145. See the exp parameter in currencies.json, it
  *   shows the number of digits past the decimal point for each currency (2 for the majority of currencies). Defaults
  *   to 0
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
  *   Pass True if you require the user's full name to complete the order
  * @param needPhoneNumber
  *   Pass True if you require the user's phone number to complete the order
  * @param needEmail
  *   Pass True if you require the user's email address to complete the order
  * @param needShippingAddress
  *   Pass True if you require the user's shipping address to complete the order
  * @param sendPhoneNumberToProvider
  *   Pass True if the user's phone number should be sent to provider
  * @param sendEmailToProvider
  *   Pass True if the user's email address should be sent to provider
  * @param isFlexible
  *   Pass True if the final price depends on the shipping method
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
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
  providerToken: String,
  currency: String,
  messageThreadId: Option[Int] = Option.empty,
  prices: List[LabeledPrice] = List.empty,
  maxTipAmount: Option[Int] = Option.empty,
  suggestedTipAmounts: List[Int] = List.empty,
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
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
