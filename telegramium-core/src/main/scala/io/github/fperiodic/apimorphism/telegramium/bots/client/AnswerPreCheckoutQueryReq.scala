package io.github.fperiodic.apimorphism.telegramium.bots.client

final case class AnswerPreCheckoutQueryReq(
                                           /** Unique identifier for the query to be answered*/
                                           preCheckoutQueryId: String,
                                           /** Specify True if everything is alright (goods are available,
                                             * etc.) and the bot is ready to proceed with the order. Use
                                             * False if there are any problems.*/
                                           ok: Boolean,
                                           /** Required if ok is False. Error message in human readable
                                             * form that explains the reason for failure to proceed with
                                             * the checkout (e.g. "Sorry, somebody just bought the last of
                                             * our amazing black T-shirts while you were busy filling out
                                             * your payment details. Please choose a different color or
                                             * garment!"). Telegram will display this message to the user.*/
                                           errorMessage: Option[String] = Option.empty)
