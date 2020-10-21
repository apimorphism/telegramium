package telegramium.bots.client

import telegramium.bots.LabeledPrice
import telegramium.bots.InlineKeyboardMarkup

final case class SendInvoiceReq(
                                /** Unique identifier for the target private chat*/
                                chatId: Int,
                                /** Product name, 1-32 characters*/
                                title: String,
                                /** Product description, 1-255 characters*/
                                description: String,
                                /** Bot-defined invoice payload, 1-128 bytes. This will not be
                                  * displayed to the user, use for your internal processes.*/
                                payload: String,
                                /** Payments provider token, obtained via Botfather*/
                                providerToken: String,
                                /** Unique deep-linking parameter that can be used to generate
                                  * this invoice when used as a start parameter*/
                                startParameter: String,
                                /** Three-letter ISO 4217 currency code, see more on currencies*/
                                currency: String,
                                /** Price breakdown, a JSON-serialized list of components (e.g.
                                  * product price, tax, discount, delivery cost, delivery tax,
                                  * bonus, etc.)*/
                                prices: List[LabeledPrice] = List.empty,
                                /** A JSON-serialized data about the invoice, which will be
                                  * shared with the payment provider. A detailed description of
                                  * required fields should be provided by the payment provider.*/
                                providerData: Option[String] = Option.empty,
                                /** URL of the product photo for the invoice. Can be a photo of
                                  * the goods or a marketing image for a service. People like it
                                  * better when they see what they are paying for.*/
                                photoUrl: Option[String] = Option.empty,
                                /** Photo size*/
                                photoSize: Option[Int] = Option.empty,
                                /** Photo width*/
                                photoWidth: Option[Int] = Option.empty,
                                /** Photo height*/
                                photoHeight: Option[Int] = Option.empty,
                                /** Pass True, if you require the user's full name to complete
                                  * the order*/
                                needName: Option[Boolean] = Option.empty,
                                /** Pass True, if you require the user's phone number to
                                  * complete the order*/
                                needPhoneNumber: Option[Boolean] = Option.empty,
                                /** Pass True, if you require the user's email address to
                                  * complete the order*/
                                needEmail: Option[Boolean] = Option.empty,
                                /** Pass True, if you require the user's shipping address to
                                  * complete the order*/
                                needShippingAddress: Option[Boolean] = Option.empty,
                                /** Pass True, if user's phone number should be sent to
                                  * provider*/
                                sendPhoneNumberToProvider: Option[Boolean] = Option.empty,
                                /** Pass True, if user's email address should be sent to
                                  * provider*/
                                sendEmailToProvider: Option[Boolean] = Option.empty,
                                /** Pass True, if the final price depends on the shipping
                                  * method*/
                                isFlexible: Option[Boolean] = Option.empty,
                                /** Sends the message silently. Users will receive a
                                  * notification with no sound.*/
                                disableNotification: Option[Boolean] = Option.empty,
                                /** If the message is a reply, ID of the original message*/
                                replyToMessageId: Option[Int] = Option.empty,
                                /** A JSON-serialized object for an inline keyboard. If empty,
                                  * one 'Pay total price' button will be shown. If not empty,
                                  * the first button must be a Pay button.*/
                                replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
