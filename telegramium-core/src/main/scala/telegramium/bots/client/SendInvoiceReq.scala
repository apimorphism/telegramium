package telegramium.bots.client

import telegramium.bots.LabeledPrice
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId Unique identifier for the target private chat
  * @param title Product name, 1-32 characters
  * @param description Product description, 1-255 characters
  * @param payload Bot-defined invoice payload, 1-128 bytes. This will not be
  * displayed to the user, use for your internal processes.
  * @param providerToken Payments provider token, obtained via Botfather
  * @param startParameter Unique deep-linking parameter that can be used to generate
  * this invoice when used as a start parameter
  * @param currency Three-letter ISO 4217 currency code, see more on currencies
  * @param prices Price breakdown, a JSON-serialized list of components (e.g.
  * product price, tax, discount, delivery cost, delivery tax,
  * bonus, etc.)
  * @param providerData A JSON-serialized data about the invoice, which will be
  * shared with the payment provider. A detailed description of
  * required fields should be provided by the payment provider.
  * @param photoUrl URL of the product photo for the invoice. Can be a photo of
  * the goods or a marketing image for a service. People like it
  * better when they see what they are paying for.
  * @param photoSize Photo size
  * @param photoWidth Photo width
  * @param photoHeight Photo height
  * @param needName Pass True, if you require the user's full name to complete
  * the order
  * @param needPhoneNumber Pass True, if you require the user's phone number to
  * complete the order
  * @param needEmail Pass True, if you require the user's email address to
  * complete the order
  * @param needShippingAddress Pass True, if you require the user's shipping address to
  * complete the order
  * @param sendPhoneNumberToProvider Pass True, if user's phone number should be sent to
  * provider
  * @param sendEmailToProvider Pass True, if user's email address should be sent to
  * provider
  * @param isFlexible Pass True, if the final price depends on the shipping
  * method
  * @param disableNotification Sends the message silently. Users will receive a
  * notification with no sound.
  * @param replyToMessageId If the message is a reply, ID of the original message
  * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
  * specified replied-to message is not found
  * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty,
  * one 'Pay total price' button will be shown. If not empty,
  * the first button must be a Pay button. */
final case class SendInvoiceReq(chatId: Int,
                                title: String,
                                description: String,
                                payload: String,
                                providerToken: String,
                                startParameter: String,
                                currency: String,
                                prices: List[LabeledPrice] = List.empty,
                                providerData: Option[String] = Option.empty,
                                photoUrl: Option[String] = Option.empty,
                                photoSize: Option[Int] = Option.empty,
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
                                replyToMessageId: Option[Int] = Option.empty,
                                allowSendingWithoutReply: Option[Boolean] = Option.empty,
                                replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
