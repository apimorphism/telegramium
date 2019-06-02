package io.github.fperiodic.apimorphism.telegramium.bots

final case class HandleUpdateReq(
                                 /** The update‘s unique identifier. Update identifiers start
                                   * from a certain positive number and increase sequentially.
                                   * This ID becomes especially handy if you’re using Webhooks,
                                   * since it allows you to ignore repeated updates or to restore
                                   * the correct update sequence, should they get out of order.
                                   * If there are no new updates for at least a week, then
                                   * identifier of the next update will be chosen randomly
                                   * instead of sequentially.*/
                                 updateId: Int,
                                 /** Optional. New incoming message of any kind — text, photo,
                                   * sticker, etc.*/
                                 message: Option[Message] = Option.empty,
                                 /** Optional. New version of a message that is known to the bot
                                   * and was edited*/
                                 editedMessage: Option[Message] = Option.empty,
                                 /** Optional. New incoming channel post of any kind — text,
                                   * photo, sticker, etc.*/
                                 channelPost: Option[Message] = Option.empty,
                                 /** Optional. New version of a channel post that is known to
                                   * the bot and was edited*/
                                 editedChannelPost: Option[Message] = Option.empty,
                                 /** Optional. New incoming inline query*/
                                 inlineQuery: Option[InlineQuery] = Option.empty,
                                 /** Optional. The result of an inline query that was chosen by
                                   * a user and sent to their chat partner. Please see our
                                   * documentation on the feedback collecting for details on how
                                   * to enable these updates for your bot.*/
                                 chosenInlineResult: Option[ChosenInlineResult] = Option.empty,
                                 /** Optional. New incoming callback query*/
                                 callbackQuery: Option[CallbackQuery] = Option.empty,
                                 /** Optional. New incoming shipping query. Only for invoices
                                   * with flexible price*/
                                 shippingQuery: Option[ShippingQuery] = Option.empty,
                                 /** Optional. New incoming pre-checkout query. Contains full
                                   * information about checkout*/
                                 preCheckoutQuery: Option[PreCheckoutQuery] = Option.empty,
                                 /** Optional. New poll state. Bots receive only updates about
                                   * polls, which are sent or stopped by the bot*/
                                 poll: Option[Poll] = Option.empty)
