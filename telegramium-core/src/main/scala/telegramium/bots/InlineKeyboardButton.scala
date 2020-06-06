package telegramium.bots

/** This object represents one button of an inline keyboard. You must use exactly
  * one of the optional fields.*/
final case class InlineKeyboardButton(
                                      /** Label text on the button*/
                                      text: String,
                                      /** Optional. HTTP or tg:// url to be opened when button is
                                        * pressed*/
                                      url: Option[String] = Option.empty,
                                      /** Optional. An HTTP URL used to automatically authorize the
                                        * user. Can be used as a replacement for the Telegram Login
                                        * Widget.*/
                                      loginUrl: Option[LoginUrl] = Option.empty,
                                      /** Optional. Data to be sent in a callback query to the bot
                                        * when button is pressed, 1-64 bytes*/
                                      callbackData: Option[String] = Option.empty,
                                      /** Optional. If set, pressing the button will prompt the user
                                        * to select one of their chats, open that chat and insert the
                                        * bot's username and the specified inline query in the input
                                        * field. Can be empty, in which case just the bot's username
                                        * will be inserted. Note: This offers an easy way for users to
                                        * start using your bot in inline mode when they are currently
                                        * in a private chat with it. Especially useful when combined
                                        * with switch_pm… actions – in this case the user will be
                                        * automatically returned to the chat they switched from,
                                        * skipping the chat selection screen.*/
                                      switchInlineQuery: Option[String] = Option.empty,
                                      /** Optional. If set, pressing the button will insert the bot's
                                        * username and the specified inline query in the current
                                        * chat's input field. Can be empty, in which case only the
                                        * bot's username will be inserted. This offers a quick way for
                                        * the user to open your bot in inline mode in the same chat –
                                        * good for selecting something from multiple options.*/
                                      switchInlineQueryCurrentChat: Option[String] = Option.empty,
                                      /** Optional. Description of the game that will be launched
                                        * when the user presses the button. NOTE: This type of button
                                        * must always be the first button in the first row.*/
                                      callbackGame: Option[String] = Option.empty,
                                      /** Optional. Specify True, to send a Pay button. NOTE: This
                                        * type of button must always be the first button in the first
                                        * row.*/
                                      pay: Option[Boolean] = Option.empty)
